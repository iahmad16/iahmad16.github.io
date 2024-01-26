// Responsible for highlighting sidebar links based on current page
document.addEventListener('DOMContentLoaded', function () {
    let currentPath = window.location.pathname;
    let options = document.querySelectorAll("li");
    options.forEach(function (option) {

        const link = option.querySelector("a");
        if (link.getAttribute('href') === currentPath) {
            link.style.color = '#AE3236';
        }
    });
});

// document.addEventListener('DOMContentLoaded', function () {
//     // Function to handle animation when Alwakalat Card is pressed
//     function animateAlwakalatCard() {
//         // Set initial animation state
//         document.body.style.background = 'linear-gradient(180deg, #000000 0%, #00000000 100%) 0% 0% no-repeat padding-box';
//         document.body.style.transform = 'translateY(-10%)';
//         document.body.style.opacity = '0';
//
//         document.getElementById('red-box').style.height = '50%';
//         document.getElementById('red-box').style.opacity = '1';
//
//         document.querySelectorAll('h1, h3, #buttons, #images img').forEach(function (element) {
//             element.style.opacity = '0';
//         });
//
//         document.getElementById('images').style.transform = 'translateX(-100%)';
//
//         // Simulate loading new content
//         setTimeout(function () {
//             // Change background and content
//             document.body.style.background = 'new_background'; // Set the background of the new page
//             document.body.style.transform = 'translateY(0)';
//             document.body.style.opacity = '1';
//
//             document.getElementById('red-box').style.height = '0';
//             document.getElementById('red-box').style.opacity = '0';
//
//             document.querySelectorAll('h1, h3, #buttons, #images img').forEach(function (element) {
//                 element.style.opacity = '1';
//             });
//
//             document.getElementById('images').style.transform = 'translateX(0)';
//         }, 1000); // Adjust the delay based on the duration of the animations
//     }
//
//     // Attach the animation function to the Alwakalat Card button
//     var alwakalatCardButton = document.querySelector('a[href="/alwakalat-card"]');
//     alwakalatCardButton.addEventListener('click', function (event) {
//         event.preventDefault();
//         animateAlwakalatCard();
//     });
// });




function display_website_home(){
    window.location.href = '/';
}

function display_download_page(){
    window.location.href = '/download';
}

function fadeOutAndRedirect(destination) {
    const elementsToFadeOut = document.querySelectorAll("h1, h3, #buttons");
    const phonesleft = document.querySelectorAll("#second-left, #first-left");
    const phonesright = document.querySelectorAll("#second-right, #first-right");
    const phones = document.getElementById("center");
    const redbox = document.getElementById("red-box");

    phonesleft.forEach(phoneL => phoneL.classList.add("converge-left"));
    phonesright.forEach(phoneR => phoneR.classList.add("converge-right"));
    phones.classList.add("diverge");
    redbox.classList.add("final-state");

    // Add a CSS class to initiate the fade-out animation
    elementsToFadeOut.forEach(element => element.classList.add("fade-out"));


    // Redirect to the specified page after a delay
    setTimeout(() => {
        window.location.href = destination;
    }, 1400); // Adjust the delay based on your transition duration
}

function display_alwakalat_card_page(){
    window.location.href = '/alwakalat-card';
}