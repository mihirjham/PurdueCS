To compile the code:
  ant

To run the editor:
  ant editor

If you get a java.lang.OutOfMemoryError exception when trying to run the
editor, then you need to increase the maximum heap size. There are two ways to
do this (depending on how you're running the editor):
1) Modify the build.xml file in the "editor" task, so that the java command
   looks like this:
   <java classname="mappapp.MapEditor" fork="true" failonerror="true" maxmemory="128m">
2) Increase the heap size via the command line using the -Xmx128m option to the
   "java" command.
