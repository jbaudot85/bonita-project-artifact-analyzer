# bonita-artifact-counter

This piece of software is a console app that seek for artifacts number and usage in Bonita projects (as stored in github repos).
So far the projects must already be cloned from github on the local disk, and when analysing many, be direct childs of a single parent folder.
A single CSV file is exported, with a line per project.

Note: widget usage is counted both:
* by direct integration in a page;
* by indirect integration through a fragment.

Plain Java 8 code developped in VSCode editor with Project Manager for Java extension.

Application should be directly taylored and ran using the *Main()* method of the class *App*.
