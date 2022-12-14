package cn.org.opendfl.base;

import java.util.Collection;

public abstract interface IPageVO<T>
{
  public abstract int getTotal();

  public abstract int getPageSize();

  public abstract int getTotalPage();

  public abstract int getCurrentPage();

  public abstract Collection<T> getRows();
}
