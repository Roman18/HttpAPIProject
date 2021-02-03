package com.company.Menu.MenuAction;



public interface MenuAction {
    void doAction(String token);
    String getName();
    boolean closeAfter();

}
