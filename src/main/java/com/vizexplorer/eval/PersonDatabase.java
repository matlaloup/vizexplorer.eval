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
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This object is use for easy serialization of a list of persons with JACKSON.
 *
 */
public class PersonDatabase
{

  private Set<Person> persons;

  public Set<Person> getPersons()
  {
    return persons;
  }

  public void setPersons(Set<Person> persons)
  {
    this.persons = persons;
  }

  public PersonDatabase()
  {
  }

  public PersonDatabase(Collection<Person> persons)
  {
    if (persons == null)
    {
      this.persons = new LinkedHashSet<>();
    }
    else
    {
      if (persons instanceof LinkedHashSet)
      {
        this.persons = (LinkedHashSet<Person>) persons;
      }
      else
      {
        this.persons = new LinkedHashSet<>(persons);
      }
    }
  }

  /**
   * @param inputFile
   * @return the persons in the provided JSON inputFile
   * @throws DatabaseException
   */
  public static Set<Person> readFromJSONFile(File inputFile) throws DatabaseException
  {
    ObjectMapper mapper = new ObjectMapper();
    try
    {
      PersonDatabase db = mapper.readValue(inputFile, PersonDatabase.class);
      return db.persons;
    }
    catch (Exception e)
    {
      throw new DatabaseException("Unable to read database file !", e);
    }
  }

  /**
   * Write the list of person as JSON content onto the outputFile. Overrides its
   * content.
   * 
   * @param persons
   * @param outputFile
   * @throws DatabaseException
   */
  public static void writeToJSONFile(Set<Person> persons, File outputFile) throws DatabaseException
  {
    ObjectMapper mapper = new ObjectMapper();
    try
    {
      mapper.writeValue(outputFile, new PersonDatabase(persons));
    }
    catch (Exception e)
    {
      throw new DatabaseException("Unable to read database file !", e);
    }
  }

}
