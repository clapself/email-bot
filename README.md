# EmailBot
The EmailBot is a web based solution for email marketing.

# Problem Statement
Email marketing is one of the most effective ways to market products or services.There are many free and paid solutions that provide email marketing services. The challenge is that the free ones are not easily scalable and paid ones cost a fortune and often require significant upfront training.

Through this project, which is starting as an internship project, our goal is to collaboratively build an easy to use web based solution for email marketing that’s scalable while being free for all.

# Tech Stack
- Java SpringBoot based Restful Web Services
- MySQL as a database
- ReactJS, AntD, HTML/CSS for the frontend

# Current State
In its current state, the project contains the core backend code for constructing emails using a simple templating system and sending them out via spring mail.

## Core Components
1. Email Template: Each type of email that needs to be sent out will have an associated Email Template File. An email template is .txt file that has a special format. Please see a sample of it at: https://github.com/clapself/email-bot/blob/main/src/main/resources/templates/emailresetpassword.txt.
2. Template Processor: A java class that reads the provided Template File, parses its content out and replaces any dynamic variables with the provided data map
3. Email Service: A component that constructs the final email message, sends it out using spring mailer.

# Who's Using EmailBot
Clapself (https://www.clapself.com), a smart e-learning platform for personal development, uses a version of EmailBot for communicating with its community members.
