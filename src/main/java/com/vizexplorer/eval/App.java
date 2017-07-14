/**
 * Copyright Notice Component
 * Our software code should contain a copyright notice to prevent an infringer from claiming that it did not know the code was protected under copyright.  The notice should look like this:
 * Copyright © 2008 - 2016 NEW BIS SAFE LUXCO S.Á.R.L 
 * The copyright start date should represent the oldest elements in the code and the end date should represent the newest elements in the code.
 * 
 * Licensing Component
 * 
 * Under our standard VizExplorer license agreements we license our "Software".  "Software" has the definition below:
 * 
 * “Software” means: the VizExplorer client application and/or server application software (as applicable, and in executable form only) provided to Customer by Supplier or with Supplier’s written consent, and (i) all user guides, manuals and other user documentation relating to the Software (whether provided in hard-copy, electronically or on-line); (ii) all enhancements, modifications, updates, new releases that may, from time to time, be provided to Customer by Supplier or with Supplier’s written consent; and (iii) all additional VizExplorer software code (including, but not limited to, SQL interface code) but excluding all source code, that may, from time to time, be provided to Customer by Supplier or with Supplier’s written consent;
 * 
 * In addition our standard VizExplorer license agreement provides:
 * No license or subscription is granted for any source code and no license or right is granted to modify, adapt, create a derivative work, merge, or translate the Software without the prior written consent of Supplier.
 * Accordingly, VizExplorer does grant a license to the software in executable form, but does not grant a license for source code.
 * 
 * Intellectual Property Notice
 * Based on the above, I suggest the following generic Intellectual Property Notice:
 * 
 *  “The following Intellectual Property Notice applies to all software code below and replaces any similar notice contained in this software.
 * INTELLECTUAL PROPERTY NOTICE
 * Copyright © 2008 - 2016 NEW BIS SAFE LUXCO S.Á.R.L 
 * The VizExplorer software code below is a component of a VizExplorer software solution. 
 *  If the software code below is in non-source code executable form, then the code is licensed to you on the terms and conditions of VizExplorer’s standard End User License Agreement, a copy of which is located on VizExplorer’s website at: http://www.vizexplorer.com/license-agreements/.   By using the software code you agree that you have read and accepted the terms and conditions of VizExplorer’s standard End User License Agreement.
 * If the software code below is in source code form, no license or other permission is granted to you to use the software code."
 * 
 */
package com.vizexplorer.eval;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Main class
 */
public class App

{

  private PersonDAO personDAO;

  private CommandType commandType;

  private String[] args;

  public App(CommandType action, String[] args) throws DatabaseException
  {
    this.commandType = action;
    this.args = args;
    this.personDAO = new PersonDAO();
  }

  public static void main(String[] args) throws ParseException, DatabaseException
  {

    if (args.length < 2)
    {
      handleError("Invalid syntax : At least two parameters expected");
      return;
    }

    String commandArg = args[0];
    // parse the action to execute
    CommandType commandType = CommandType.resolve(commandArg);
    if (commandType == null)
    {
      handleError("Invalid syntax : First parameter must be among : [get|create|update|delete]");
      return;
    }

    App app = new App(commandType, args);
    app.execute();
  }

  /**
   * Main method for executing the app.
   * 
   * @throws ParseException
   * @throws DatabaseException
   */
  private void execute() throws DatabaseException, ParseException
  {
    switch (commandType)
    {
    case GET:
      handleGet();
      break;
    case DELETE:
      handleDelete();
      break;
    case CREATE:
      handleCreate();
      break;
    case UPDATE:
      handleUpdate();
      break;
    }

  }

  private void handleGet() throws DatabaseException
  {
    String id = args[1];

    Person person = personDAO.get(id);
    System.out.println("Person instance found: " + person);
  }

  private void handleDelete() throws DatabaseException
  {
    String id = args[1];

    personDAO.delete(id);
    System.out.println("Person successfully deleted!");

  }

  private void handleCreate() throws DatabaseException, ParseException
  {
    if (args.length < 4)
    {
      handleError("Invalid syntax : create command takes 3 parameters : name, gender and birth date");
      return;
    }
    String birthDateAsString = args[3];
    Date birthDate = parseDate(birthDateAsString);
    Person person = new Person(args[1], args[2], birthDate);

    personDAO.insert(person);
    System.out.println("Person instance created: " + person);
  }

  private void handleUpdate() throws DatabaseException, ParseException
  {
    if (args.length < 5)
    {
      handleError("Invalid syntax : update command takes 4 parameters : id, name, gender and birth date");
      return;
    }
    String id = args[1];

    Person existingPerson = personDAO.get(id);

    Date birthDate = parseDate(args[4]);
    existingPerson.setName(args[2]);
    existingPerson.setGender(args[3]);
    existingPerson.setBirthDate(birthDate);

    personDAO.update(existingPerson);
    System.out.println("Person successfully updated !");
  }

  // Utility methods

  private static void handleError(String message)
  {
    throw new InvalidParameterException(message);
  }

  private static Date parseDate(String birthDateAsString) throws ParseException
  {
    Date birthDate = new SimpleDateFormat("yyyyMMdd").parse(birthDateAsString);
    return birthDate;
  }

}
