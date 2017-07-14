/**
 * Copyright Notice Component
 * Our software code should contain a copyright notice to prevent an infringer from claiming that it did not know the code was protected under copyright.  The notice should look like this:
 * Copyright � 2008 - 2016 NEW BIS SAFE LUXCO S.�.R.L 
 * The copyright start date should represent the oldest elements in the code and the end date should represent the newest elements in the code.
 * 
 * Licensing Component
 * 
 * Under our standard VizExplorer license agreements we license our "Software".  "Software" has the definition below:
 * 
 * �Software� means: the VizExplorer client application and/or server application software (as applicable, and in executable form only) provided to Customer by Supplier or with Supplier�s written consent, and (i) all user guides, manuals and other user documentation relating to the Software (whether provided in hard-copy, electronically or on-line); (ii) all enhancements, modifications, updates, new releases that may, from time to time, be provided to Customer by Supplier or with Supplier�s written consent; and (iii) all additional VizExplorer software code (including, but not limited to, SQL interface code) but excluding all source code, that may, from time to time, be provided to Customer by Supplier or with Supplier�s written consent;
 * 
 * In addition our standard VizExplorer license agreement provides:
 * No license or subscription is granted for any source code and no license or right is granted to modify, adapt, create a derivative work, merge, or translate the Software without the prior written consent of Supplier.
 * Accordingly, VizExplorer does grant a license to the software in executable form, but does not grant a license for source code.
 * 
 * Intellectual Property Notice
 * Based on the above, I suggest the following generic Intellectual Property Notice:
 * 
 *  �The following Intellectual Property Notice applies to all software code below and replaces any similar notice contained in this software.
 * INTELLECTUAL PROPERTY NOTICE
 * Copyright � 2008 - 2016 NEW BIS SAFE LUXCO S.�.R.L 
 * The VizExplorer software code below is a component of a VizExplorer software solution. 
 *  If the software code below is in non-source code executable form, then the code is licensed to you on the terms and conditions of VizExplorer�s standard End User License Agreement, a copy of which is located on VizExplorer�s website at: http://www.vizexplorer.com/license-agreements/.   By using the software code you agree that you have read and accepted the terms and conditions of VizExplorer�s standard End User License Agreement.
 * If the software code below is in source code form, no license or other permission is granted to you to use the software code."
 * 
 */
package com.vizexplorer.eval;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Data Access Object of {@link Person} objects.<br/>
 * Warning : This class is NOT thread safe.
 *
 */
public class PersonDAO
{

  private File databaseFile = new File("database/persons.json");

  private Map<String, Person> personsById = new LinkedHashMap<>();

  public PersonDAO() throws DatabaseException
  {
    load();
  }

  /**
   * load the database content
   * 
   * @throws DatabaseException
   */
  private void load() throws DatabaseException
  {
    if (!databaseFile.exists())
    {
      initDatabaseFile();
    }
    else
    {
      Set<Person> persons = PersonDatabase.readFromJSONFile(databaseFile);
      for (Person person : persons)
      {
        personsById.put(person.getId(), person);
      }
    }

  }

  private void initDatabaseFile() throws DatabaseException
  {
    try
    {
      databaseFile.getParentFile().mkdirs();
      databaseFile.createNewFile();
      // creates the empty db, to allow ready an empty db.
      PersonDatabase.writeToJSONFile(new HashSet<>(), databaseFile);
    }
    catch (IOException e)
    {
      throw new DatabaseException("Unable to initialize database", e);
    }
  }

  /**
   * @param id
   * @return the person for the given id
   * @throws DatabaseException
   *           if the person does not exist
   */
  public Person get(String id) throws DatabaseException
  {
    Person person = personsById.get(id);
    if (person == null)
    {
      throw new DatabaseException("There is no person in database with id #" + id);
    }
    return person;
  }

  /**
   * Insert the person in the database
   * 
   * @param person
   * @throws DatabaseException
   *           if there is already a person with same id.
   */
  public void insert(Person person) throws DatabaseException
  {
    String id = person.getId();
    if (personsById.containsKey(id))
    {
      throw new DatabaseException("There is already a person in database with id #" + id);
    }
    else
    {
      personsById.put(id, person);
      save();
    }
  }

  /**
   * Deletes a person in the database, using its id.
   * 
   * @param id
   * @throws DatabaseException
   *           if there is no person in the database for the provided id
   */
  public void delete(String id) throws DatabaseException
  {
    Person person = personsById.get(id);
    if (person == null)
    {
      throw new DatabaseException("There is no person in database with id #" + id);
    }
    else
    {
      personsById.remove(id);
      save();
    }
  }

  /**
   * Update a person in the database
   * 
   * @param person
   * @throws DatabaseException
   *           if no person could be found in the database with the same id.
   */
  public void update(Person person) throws DatabaseException
  {
    String id = person.getId();
    Person existingPerson = personsById.get(id);
    if (existingPerson == null)
    {
      throw new DatabaseException("There is no person in database with id #" + id);
    }
    else
    {
      personsById.put(id, person);
      save();
    }
  }

  /**
   * Saves all the persons in the database
   * 
   * @throws DatabaseException
   */
  private void save() throws DatabaseException
  {
    Set<Person> persons = new LinkedHashSet<>(personsById.values());
    PersonDatabase.writeToJSONFile(persons, databaseFile);
  }

}
