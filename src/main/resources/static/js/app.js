document.addEventListener('DOMContentLoaded', function () {

    // ---- Set Page Attributes ----
    
        // Grab Username and userId
        userDetails();

        // Set min-date on the date input
        restrictDates();

    
    // ---- Button Event Listeners ----
    
        // Header: Home & Profile buttons
        document.querySelector('#heading').onclick = home_view;
        document.querySelector('#profile-btn').onclick = profile_view;
        
        // Home: Search button
        document.querySelector('#search-form').onsubmit = (event) => results_view(event);

        // Results: Change Date button
        document.querySelector('#change-date').onclick = home_view;

        // Profile: Return home button
        document.querySelector('#home-btn').onclick = home_view;

});


// --- Set Today's Date ----

let today = new Date;
const yyyy = today.getFullYear();
const mm = String(today.getMonth() + 1).padStart(2, "0"); 
const dd = String(today.getDate()).padStart(2, "0");
today = `${yyyy}-${mm}-${dd}`;


// ---- Home Page ("/") ----

function home_view() {

    // Clear past input on form
    document.querySelector('#search-form').reset();
    
    // Hide other views and load home screen
    document.querySelector('#results').style.display = 'none';
    document.querySelector('#profile').style.display = 'none';
    document.querySelector('#home').style.display = 'flex';

}


// ---- Results Page ("/results") ----

async function results_view(event) {

    // Prevent form-submission from reloading the page
    event.preventDefault()


    // Erase the old results list
    const carCards = document.querySelector('.car-cards');
    carCards.innerHTML = "";

    // Hide other views and load results screen
    document.querySelector('#home').style.display = 'none';
    document.querySelector('#profile').style.display = 'none';
    document.querySelector('#results').style.display = 'flex';

    // Set date label above search results
    const dateLabel = document.querySelector("#selected-date")
    const searchDate = document.querySelector('#date').value;
    dateLabel.dataset.date = searchDate;
    const dateComponents = searchDate.split("-");
    dateLabel.innerHTML = `${dateComponents[2]} / ${dateComponents[1]} / ${dateComponents[0]}`;
    
    // Query API for available cars
    const response = await fetch(`/api/cars?date=${searchDate}`, {
            method: 'GET',
        });

        if (!response.ok) {
            // Report error
            const error = await response.json();
            console.log(error);
            showPopup("Something went wrong.");
            home_view();
            return;
        } 

        cars = await response.json();

        // Turn each car into an element and append it to the page
        cars.forEach(car => {
            const card = document.createElement('div');
            card.classList.add('car-card');
            card.dataset.id = car.id;
            card.innerHTML =
                `<h2>${car.make} ${car.model}</h2>` +
                `<p class='${car.make}'>Make: ${car.make}</p>` +
                `<p>Model: ${car.model}</p>`;
            const btn = document.createElement('button');
            btn.innerHTML = 'Reserve';
            btn.addEventListener('click', () => {
                const carId = parseInt(btn.closest('.car-card').dataset.id);
                makeReservation(carId);
            });
            card.append(btn);
            carCards.append(card);
        })

}


// ---- Profile Page ("/profile") ----

async function profile_view() {
    
    // Empty the table body content to prepare for new data
    const table = document.querySelector('tbody');
    table.innerHTML = "";
    
    // Hide the other views and display the profile view
    document.querySelector('#home').style.display = 'none';
    document.querySelector('#results').style.display = 'none';
    document.querySelector('#profile').style.display = 'flex';

    // Request the user's reservations
    const userId = document.querySelector('#profile-btn').dataset.id;
    const response = await fetch(`/api/reservations?userId=${userId}`);
    if (!response.ok) {
        // Report error
        const error = await response.json();
        console.log(error);
        showPopup("Something went wrong.");
        return;
    } 

    // Take the response and fill out the table rows
    const reservations = await response.json();
    
    reservations.forEach(reservation => {

        // Format date:
        const date = reservation.date.replaceAll("-", " / ");

        const row = document.createElement('tr');
        row.dataset.id = reservation.id;
        row.innerHTML = 
            `<td>${date}</td>` +
            `<td>${reservation.car.make} ${reservation.car.model}</td>`;

        // Add cancel button if the reservation isn't in the past
        const reservationDate = new Date(reservation.date);
        const todayDate = new Date(today);

        if (reservationDate >= todayDate) {
            row.innerHTML += `<td><button>Cancel</button></td>`;    
            row.querySelector('button').onclick = () => deleteReservation(reservation.id);
        } else {
            row.innerHTML += `<td>Completed</td>`;    
        }

        table.append(row);
    })

}


// ---- Create a New Reservation

async function makeReservation(car_id) {
    const response = await fetch('api/reservations', {
        method: "POST",
        headers: {"content-type": "application/json"},
        body: JSON.stringify({
            userId: document.querySelector('#profile-btn').dataset.id,
            carId: car_id,
            date: document.querySelector('#selected-date').dataset.date
        })
    });

    if (!response.ok) {
        // Report error
        const error = await response.json();
        console.log(error);
        showPopup("Car already booked, try another date.");
        return;
    } 

    // Display success message and redirect to profile
    showPopup("Reservation successful!");
    profile_view();

}


// ---- Delete a Reservation ----

async function deleteReservation(id) {

    const response = await fetch(`/api/reservations/${id}`, {
        method: "DELETE"
    });

    if (!response.ok) {
        // Report error
        const error = await response.json();
        console.log(error);
        showPopup("Something went wrong.");
        return;
    } 

    // Display success message and refresh profile
    showPopup("Reservation successfully cancelled!");
    profile_view();

}


// ---- Store user details in DOM ----

async function userDetails() {
    
    // Fetch user details from the server
    const response = await fetch('/api/users/me');
    
    if (!response.ok) {
        // Report error
        const error = await response.json();
        console.log(error);
        return;
    } 

    const user = await response.json();

    // Add User id to profile button dataset
    const btn = document.querySelector('#profile-btn');
    btn.dataset.id = user.id;
    const username = user.username[0].toUpperCase() + user.username.slice(1);
    btn.innerHTML = `${username}'s Profile`;

}


// ---- Restrict past dates on the form date input ----

function restrictDates() {

    // Set min date on form input
    document.querySelector('#date').min = `${yyyy}-${mm}-${dd}`;

}


// ---- Message Popup ----

function showPopup(message, timeout = 3000) {

    const popup = document.querySelector('#popup');
    popup.querySelector('p').textContent = message;
    popup.classList.add('show');  
    setTimeout(() => popup.classList.remove('show'), timeout);

}

function closePopup() {

    document.querySelector('#popup').classList.remove('show');

}


// TODO ---- Change Username/Password ----


// TODO ---- Change Reservation Details (Car/Date) ----