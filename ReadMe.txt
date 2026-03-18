


ITEC 4020 – Internet Client-Server Systems
Group 4 Project
Secure Web Information System

1. Project Description

This project implements a prototype Secure Web Information System
using Java Servlets, JSP, HTML, CSS, and JavaScript.

The system demonstrates:

• User authentication
• Session management
• Protected resources
• Secure image access using servlets
• Content protection techniques (print, copy, drag restrictions)

After logging in, users can access protected pages such as:

• About Me page
• Courses page

2. Requirements

The following software is required to run this project:

• Java JDK 8 or later
• Apache Tomcat (already installed on SIT server)
• York University VPN
• SSH client (Linux / Mac Terminal or Windows PowerShell / Git Bash)
• Web browser


3. Downloading the Project

Download the project folder from the repository or submission package.
The project folder structure should look like:
website/
├── css
│   ├── aboutme.css
│   ├── courses.css
│   ├── credit.css
│   ├── login.css
│   ├── preventPrint.css
│   ├── profile.css
│   ├── reset.css
│   └── useDefaultCursor.css
├── index.jsp
├── js
│   ├── disableDragging.js
│   ├── disablePrint.js
│   ├── disableSavingImg.js
│   └── disableSelection.js
└── WEB-INF
    ├── classes
    │   ├── AboutMeServlet.class
    │   ├── AuthFilter.class
    │   ├── coursesServlet.class
    │   ├── ImageServlet.class
    │   ├── LoginServlet.class
    │   ├── LogoutServlet.class
    │   └── util
    │       └── ImageHelper.class
    ├── images
    │   └── Yorku_Logo.png
    ├── lib
    │   └── servlet-api.jar
    ├── src_PRIVATE
    │   ├── AboutMeServlet.java
    │   ├── AuthFilter.java
    │   ├── coursesServlet.java
    │   ├── ImageServlet.java
    │   ├── LoginServlet.java
    │   ├── LogoutServlet.java
    │   └── util
    │       └── ImageHelper.java
    └── web.xml

4. Connect to York University VPN


Before accessing the SIT server, York VPN must be enabled.

Install and run York VPN, then connect to the York network.

Without VPN, the SIT server cannot be accessed.

5. Login to the SIT Server

Use SSH to log into the SIT server.

Command:

ssh itec4020grp4@sit.itec.yorku.ca

with password

Example session:

ssh itec4020grp4@sit.itec.yorku.ca
Are you sure you want to continue connecting (yes/no)? yes

Enter password when prompted.

After login, you should see:

[itec4020grp4@sit ~]$

Check current directory:

pwd

Expected output:

/home/itec4020grp4

6. Upload the Project Files

Upload the website directory to the SIT server.

Example using SCP from your local machine:

scp -r website itec4020grp4@sit.itec.yorku.ca:/home/itec4020grp4/

After upload, verify the files using:

ls

or view the directory tree:

tree website

Expected structure:

website
 ├── css
 ├── js
 ├── images
 ├── WEB-INF
 │   ├── classes
 │   ├── lib
 │   ├── images
 │   └── src_PRIVATE
 └── index.jsp

7. Compile the Java Servlets

Navigate to the website directory:

cd ~/website

Compile all servlet source files using the servlet API library:

javac -cp "WEB-INF/lib/servlet-api.jar" -d WEB-INF/classes \
WEB-INF/src_PRIVATE/*.java

This command compiles the Java source files and places the compiled
.class files inside:

WEB-INF/classes/

Verify compiled files:

ls WEB-INF/classes

8. Refresh Tomcat Deployment

After compiling servlets or updating files, refresh Tomcat by touching
the deployment descriptor:

touch WEB-INF/web.xml

This forces Tomcat to reload the application.

9. Access the Website

Open a web browser and go to:

http://sit.itec.yorku.ca:8080/itec4020grp4/

The login page will appear. *(or save the login page for a shortcut while in a browser to simply click and go to the webpage)*

10. Login Credentials

Username: Group4
Password: Group4

11. System Functionality

After login, users can access:

About Me Page
Displays profile information and protected images.

Courses Page
Displays a table of course information.

Logout
Ends the user session and returns to the login page.

12. Security Features Implemented

The system implements the following security mechanisms:

• Session-based authentication
• Protected resources stored inside WEB-INF
• Image access through ImageServlet
• Path traversal protection
• Browser caching disabled
• Clickjacking protection (X-Frame-Options)
• MIME sniffing protection (X-Content-Type-Options)
• Print protection using CSS
• Disabled text selection
• Disabled image dragging

13. Final Website URL

The final deployed website is accessible at:

http://sit.itec.yorku.ca:8080/itec4020grp4/




