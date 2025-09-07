# Rent-A-Car (Spring Boot Learning Project)
#### Video Demo: <TODO: ADD LINK HERE>
#### Description
This is a demo web application for a simple car rental service. The primary goal of this project was to learn the fundamentals of Spring Boot by building a practical, full-stack application.

**Features:**
- Search cars by date using a simple form.
- List available cars in styled cards with reservation buttons.
- Make reservations tied to authenticated users.
- User profile page displaying upcoming and past reservations.
- Cancel reservations (only for future dates).
- Sample data (users, cars, reservations) seeded at startup for testing/demo purposes.

**Tech Stack:**
- Backend: Java, Spring Boot, Spring Data JPA, Spring Security
- Frontend: HTML, CSS (with Sass), JavaScript (fetch API)
- Database: H2
- Build tool: Maven

**Endpoints:**
The backend exposes a simple REST API:
- GET /api/users/me – Fetch the currently authenticated user.
- POST /api/users - Create a new user.
- PATCH /api/users - Update username or user password.
- GET /api/cars – List all cars.
- GET /api/cars?date=YYYY-MM-DD – List cars available on a given date.
- GET /api/reservations?userId={id} - List all reservations of a user.
- GET /api/reservations/{id} - Fetch a single reservation.
- POST /api/reservations – Make a reservation.
- PUT /api/reservations/{id} - Update reservation details.
- DELETE /api/reservations/{id} – Cancel a reservation.

#### Notes on My Learning
**Backend:**
This project was my first full Spring Boot web application. In my initial attempt I created models, repositories and controllers, and then tried to add user authentication. I didn't understand Spring Security at all at the time, and had difficulty trying to retrofit it into my project considering all the different available methods and my lack of undestanding. I was following instructions I didn't really understand, and my project directory quickly became a mess of files and code I didn't fully understand.

So I took a break from this project to study Spring Security, and made small demo projects which authenticated users using different methods and provided different levels of access to different users. I found JWT tokens interesting, but ultimately decided to authenticate users using a JPA UserDetails Service in this app.

Armed with new knowledge, I restarted the project and aimed to make a cleaner version with more pronounced seaparation of concerns. 
On the security side, I created a 'SecurityUser' for authentication in addition to the 'User' which is stored in the database. They're conencted of course, but handle different things. I also made use of Data Transfer Objects (DTOs) for efficiency and an extra layer of assurance that sensitive data won't be revealed.
I utilised Services to handle the business logic of requests, seaparating that out from the Controllers, which simply receive requests and direct them to the right place.

In the end, I'm much happier with this version. Even if I haven't made the best decision in every case, every decision was mine, and I made them for reasons I can explain.

**Frontend:**
The main purpose of this project was to study Spring Boot, so my frontend was pretty basic and utilitarian. I had AI create a basic HTML and CSS template with junk data, and I just modified it to suit my needs. Then, I manually created a simple Single-Page Application (SPA) in plain JavaScript to fetch data from the API, generate HTML, and adjust styling.

It's pretty barebones, but it allows the backend to show off its features, so it'll do for now. I intend to add to it, and it's written in a such a way that I expect the updates will largely be new additions to the code, with only minor rewrites.

**Closing Thoughts:**
After using Django for all my previous web applications, I initially found Spring Boot to be a bit of a pain. But that was my ignorance speaking. I still have a lot to learn, but I've come to really like the structure of Spring Boot projects. Also, I just think Java code looks beautiful (excluding the mass of imports at the top of many files).

#### Future Updates
I'm not completely done with this project yet. There are a few things I want to add before I'll be fully satisfied:
- Replace the HttpBasic login with my own login.
- Add the ability to update user details to the frontend.
- Add the ability to update reservation details to the frontend.
- Implement the History API for a nicer user experience.
- Create a simple Admin Site.
- Replace the somewhat utilitarian design with something more interesting.