package de.foobar.ui.helper;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class ClassFileFilter extends FileFilter {

  @Override
  public boolean accept(File pathname) {
    return pathname.getName().endsWith(".class") || pathname.isDirectory();
  }

  @Override
  public String getDescription() {
    return "Java .class files";
  }

}
