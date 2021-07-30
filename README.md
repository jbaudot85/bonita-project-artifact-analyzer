# bonita-widget-tracker

This piece of software is a console app that seek for widgets usage in Bonita projects (as stored in github repos).
The projects must all be child of teh same parent folder.
A single CSV file is exported, with a line per project.

A widget usage is counted both:
* by direct integration in a page;
* by indirect integration through a fragment.

Plain Java 8 code developped in VSCode editor with Project Manager for Java extension.

Application should be directly taylored and ran using the *Main()* method of the class *App*.