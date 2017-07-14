package com.vizexplorer.eval;

import java.util.Date;

/**
 * Immutable implementation of {@link Person}.<br/>
 * It decorates an other person object which can be mutable.
 *
 */
public final class ImmutablePerson implements Person
{
  private Person delegate;

  /**
   * @param name
   * @param gender
   * @param birthDate
   */
  public ImmutablePerson(String name, String gender, Date birthDate)
  {
    this.delegate = new MutablePerson(name, gender, birthDate);
  }

  @Override
  public String getId()
  {
    return delegate.getId();
  }
  @Override
  public String getName()
  {
    return delegate.getName();
  }
  @Override
  public String getGender()
  {
    return delegate.getGender();
  }
  @Override
  public Date getBirthDate()
  {
    return delegate.getBirthDate();
  }

  @Override
  public ImmutablePerson clone()
  {
    // we must clone the date to avoid reproducing issue #3
    Date clonedDate = getBirthDate() == null ? null : (Date) getBirthDate().clone();
    // we must not reuse the same delegate.
    return new ImmutablePerson(getName(), getGender(), clonedDate);
  }

}
