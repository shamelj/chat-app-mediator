package org.mediator.project;

public interface Terminal {
    public String read(String displayMassage);

    public String read();

    public void write(Object massage);
}
