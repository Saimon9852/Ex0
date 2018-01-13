Wi-Fi Database Project
Authors: Ehud Plaksin, Saimon Lankri
The System- Conceptual model
The system is a user friendly GUI based software that allows the user to manage and manipulate Data of Wi-Fi networks.
Import:
The system can import csv files from the "Wigle wifi" app, or import csv files of the "system special form".
Export:
The system can export a csv file from its database. The Database is of special format that is different than "Wigles wifi" one.
The System can also export the Database to kml.
Online Database
The system shows the user the live state of the Database, if any of the files is changed the system will update is and parse it to the screen.
Filters
The system enables the user to apply filters to the database. The user can apply as many filters as he want, and of different types ( Date  ,  Location ,specific id, specific time,  specific lat/lon/alt). The system also enables to combine filters (And/Or) and negate Filters (Not).
The system can export the filters into a file for later use.
The system can also "undo" filters to return to previous state.
Algorithms
Use algorithm 1 to assert the location of a router based on scan locations containing that router.
Use algorithm 2 to assert the location of a scan based on other scans and a smart algorithm.


In Depth:
Packages: 
Filter Package that holds the Filter classes, 
Frame Package that holds the GUI Frames,
Junit Package that holds the Junit tests,
Comparator package that hold our own custom comparators.
Algorithm package that hold the Classes related to Algorithms 1 and 2.
And mypack containing mainly Database related Classes.

User Guide:
https://youtu.be/TaomT0Sj0ZQ
An extra feature I forgot to show in the previous video:
(how to see details about the filter , just put the pointer on the filter name and it will show)
https://youtu.be/1a9G1AQOaEQ
Issues:
•	Details about the filters are now visible in "tooltip" this is not as optimal and user friendly as other solutions, we will improve.
•	Junit testing, we wanted to get the System up and running, and did not have enough time to conclude all tests, for sure there are more bugs that we did not find yet.
If you find one, please tell us.
•	The thread that goes in the background and checks for modifications, as of yet doesn't handle file deletions. But only edits and additions.
•	Our date system can be improved, right now the user inserts date manually, we change that in the future to easier calendar input.
•	Separate "server" and GUI – HIGH PREORITY.
•	Comments, we need to add some more and better comments to most new classes.
•	Improve user guide video , by using a software instead of a cellphone,  showing more features, and being more clear and organized.
