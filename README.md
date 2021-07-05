1.	Motivation - 
We are going to implement this project on an android application which will give 
you the notification about the person which came in your contact and  later on detected as COVID-19 positive ,so if  the user came in contact with any person and if within 14 days that person  found to be covid-19 positive then the user gets the notification about COVID-19 testing. If you have tested positive for COVID-19, you have to share your details with our app so that we alert your contacts. It will be helpful for the people and government to get to know about the red zone areas. It can be made useful in order to control the spread of COVID-19  by helping the patients for timely check up without any delay.

2.	Aim of the Project - 
To give the notification to the user for the COVID-19 testing if the person comes
in contact with COVID-19 positive person.

3. Scope and objectives - 
Scope of our project is that our system COVID-19 Alert App will comprise the following modules: login/sign-up, case tracking module, and information module respectively. It will be helpful for the people and government to get to know about the red zone areas. It can be made useful in order to control the spread of COVID-19  by helping the patients for timely check up without any delay.
Our objective is to not collect any location or data from your mobile but only your phone’s proximity to other phones with the app is enabled. It will only notify you if you may have been  in close contact or not with someone who has tested COVID -19 positive. If you test positive for the COVID-19 virus, and you want to submit that information  into the app, it will only notify those people that may have been in contact with you.

4. Technical Approach - 
1.We are going to access the live location of users through the GPS and store it to the firebase cloud firestore database. Here, we are going to assign the unique id to the every user. If it is found that the distance between the two users is less than the safe distance then our app will store their unique id in each other’s database. Thus within 14 days if any one of them found COVID-19 positive then he/she will press the button naming positive in the app. Hence, our system scan his database and check the unique id’s of the people in his contact and send the notification to the respective people for testing though SMS or in app notification. 
2.We are marking the co-ordinates of COVID-19 patients and the co-ordinates of the people who were in the contact with the patient in last 14 days using Google map API.
3.We are differentiating them with different marker color on the map which will help the government and the people to get to know about the red zones.
4.We are giving the multi-language access, so that user can use our app in his/her preferred language.
5.We have speech to text system which will help the users to control whole app with voice commands.

5.Design
The block wise design of each block is explained as follows:
•	Registration of user is done through aadhar card or mobile number of the respective user. The user’s Id is stored into database for further use.
•	The location of the user is accessed through GPS which is shown through co-ordinates in the app.
•	The storing of UID to the users database of person came contact is done when two users or two UID’s came in less than 2m of range.
•	If any user using COVID-19 alert app detected positive for the virus, the user can update the personal data showing himself positive for virus.Further, the notification is send to the other users who came in contact with the person detected positive using the UID’s stored in the database initially


