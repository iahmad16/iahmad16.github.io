// function scrollToPage(sectionID){
//     console.log(sectionID);
//     var section = document.getElementById(sectionID);
//     section.scrollIntoView({behavior: "smooth", block: "start", inline: "nearest"});
// }


// const navLinks = document.querySelectorAll(".nav-link");
// const sections = document.querySelectorAll(".section");

// let currentSection = "None";
// window.addEventListener('scroll', () => {
//     sections.forEach(section => {
//         if(window.scrollY >= section.offsetTop){
//             currentSection = section.id;
//         }
//     });

//     // console.log(currentSection);

//     navLinks.forEach(navLink => {
//         if(navLink.id.includes(currentSection)){
//             // console.log(navLink);
//             document.querySelector('.active').classList.remove('active');
//             navLink.classList.add('active');
//         }
//     })
// })

function toggleDrawer() {
    let drawer = document.getElementById("drawer");
    drawer.style.display = (drawer.style.display === "none" || drawer.style.display === "") ? "flex" : "none";
}

const navLinks = document.querySelectorAll(".nav-link");
const sections = document.querySelectorAll(".section");

let currentSection = "None";

function setActiveNav() {
    sections.forEach(section => {
        if (window.scrollY + 10 >= section.offsetTop) {
            currentSection = section.id;
        }
    });
    const bars =  document.querySelectorAll(".bar");
    navLinks.forEach(navLink => {
        // navLink.classList.remove('active');
        if(currentSection === "AlwakalatCard" || currentSection === "Team"){

            bars.forEach(bar => bar.style.backgroundColor = '#000');
            navLink.style.color = '#000';
            if(currentSection === "Team")
            {
                document.querySelector("header button").style.color = '#000';
            }
        }
        else{
            bars.forEach(bar => bar.style.backgroundColor = '#fff');
            navLink.style.color = '#fff';
            document.querySelector("header button").style.color = '#fff';
        }

        if (navLink.id.includes(currentSection)) {
            // navLink.classList.add('active');
            navLink.style.color = '#AE3236';
        }
    });
}

// Initial setup
setActiveNav();

window.addEventListener('scroll', setActiveNav);



// Scrolls to specified section based on distance and animates frames.
// toggleDrawer indicates if the mobile menu should be toggled.
function scrollToPage(id,toggle) {
    const element = document.getElementById(id);
    const start = window.pageYOffset;
    const target = element.offsetTop;
    const distance = target - start;
    const startTime = performance.now();

    function scrollStep(timestamp) {
        const progress = Math.min((timestamp - startTime) / 2000, 1);
        window.scrollTo(0, start + distance * progress);

        if (progress < 1) {
            requestAnimationFrame(scrollStep);
        }
    }

    requestAnimationFrame(scrollStep);

    if(toggle)
    {
        toggleDrawer();
    }
}

// Example usage:

// slowScrollTo(); // Duration in milliseconds


// function scrollToPage(sectionID) {
//     console.log(sectionID);
//     var section = document.getElementById(sectionID);

//     var tl = gsap.timeline({
//         onComplete: function() {
//             section.scrollIntoView({ behavior: "smooth", block: "start", inline: "nearest" });
//         }
//     });

//     tl.to("h1", { duration: 0.5, top: "-20%" }, 'ace');
//     tl.to("h3", { duration: 0.5, top: "-20%" }, 'ace');
//     tl.to("#buttons", { duration: 0.5, top: "-20%" }, 'ace');
//     tl.to("#center", { duration: 3, right: "110%", y: "83%", zIndex: 3, ease: "Expo.easeInOut" });
//     tl.to("#first-left", { opacity: 0, duration: 1.5, left: "70%", right: "30%", ease: "Expo.easeInOut", delay: -3 });
//     tl.to("#first-right", { opacity: 0, duration: 1.5, right: "70%", ease: "Expo.easeInOut", delay: -3 });
//     tl.to("#second-left", { opacity: 0, duration: 1.5, left: "110%", ease: "Expo.easeOut", delay: -2 });
//     tl.to("#second-right", { opacity: 0, duration: 1.5, right: "110%", ease: "Expo.easeOut", delay: -2 });

//     tl.to("#red-box", { duration: 0.5, height: "55%", delay: -1.5, ease: "Expo.easeOut" });
// }

window.onload = function () {
    if(window.innerWidth <= 600){
        var tl = gsap.timeline({
            scrollTrigger:{
                trigger: "#DownloadApp",
                start: "-85% 10%",
                end: "70% 50%",
                scrub: true,
                sync: true,
                // markers: true,
            }
        });

        tl.to("#Home-title", {duration: 0.5, top: "-20%"},'ace');
        tl.to("#Home-description", {duration: 0.5, top: "-20%"}, 'ace');
        tl.to("#buttons", {duration: 0.5, top: "-20%"}, 'ace');

        tl.to("#center", {duration: 3, y: "60vh", zIndex: 3, scaleY: 1.3, scaleX: 1.2 ,ease: "Expo.easeInOut"});
        tl.to("#first-left", {opacity: 0,duration: 1.5, left: "70%", right: "30%", ease: "Expo.easeInOut", delay: -3});
        tl.to("#first-right", {opacity: 0,duration: 1.5, right: "70%", ease: "Expo.easeInOut", delay: -3});
        tl.to("#second-left", {opacity: 0,duration: 1.5, left: "110%", ease: "Expo.easeOut", delay: -2.7});
        tl.to("#second-right", {opacity: 0,duration: 1.5, right: "110%", ease: "Expo.easeOut", delay: -2.7});

        tl.to("#red-box", {duration: 3, height: "50%", delay: -1.5, ease: "Expo.easeOut"});
        tl.to("#download-details", {duration: 2, y: "-145%", x: "-147%", ease: "Power1.easeOut", delay: -3});


        var tl2 = gsap.timeline({
            scrollTrigger:{
                trigger: "#Project",
                start: "-85% 10%",
                end: "40% 50%",
                scrub: true,
                sync: true,
                // markers: true,
            }
        });

        tl2.to("#center", {duration: 0.3, right: "300%", ease: "Expo.easeInOut"}, 'ace2');
        tl2.to("#download-details", {duration: 0.5, left: "200%", opacity: 0, ease: "Expo.easeInOut", delay: -0.1},'ace2');
        tl2.to("#Project-title", {duration: 0.5, left: "30%", top: "4%", opacity: 1, ease: "Expo.easeInOut", delay: -0.7},'ace2');

        var tl8 = gsap.timeline({
            scrollTrigger:{
                trigger: "#Partners",
                start: "-85% 10%",
                end: "70% 50%",
                scrub: true,
                sync: true,
                // markers: true,
            }
        });

        tl8.to("#Partners-title", {duration: 0.5, left: "35%", top: "4%", opacity: 1, ease: "Expo.easeInOut", delay: -0.7},'ace25');

        var tl3 = gsap.timeline({
            scrollTrigger:{
                trigger: "#AlwakalatCard",
                start: "-20% 10%",
                end: "40% 50%",
                scrub: true,
                sync: true,
                // markers: true,
            }
        });

        tl3.to("#alwakalat-card-details", {duration: 0.5, left: "2%", opacity: 2, ease: "Expo.easeInOut", delay: -0.3});

        var tl4 = gsap.timeline({
            scrollTrigger:{
                trigger: "#Team",
                start: "-60% 10%",
                end: "52% 50%",
                scrub: true,
                sync: true,
                // markers: true,
            }
        });

        // tl4.to("#AlwakalatCard", {duration: 1, left: "100%", opacity: 0, ease: "Expo.easeOut"});
        tl4.to("#AlwakalatCard", {duration: 6, left: "10%", opacity: 0, ease: "Expo.easeInOut"});

        tl4.to("#AlwakalatCard h1", {duration: 5, y: "-600%", opacity: 0, ease: "Expo.easeInOut"},'toTeamPage');
        tl4.to("#AlwakalatCard h3", {duration: 5, y: "-400%", opacity: 0, ease: "Expo.easeInOut", delay: 1},'toTeamPage');
        tl4.to("#AlwakalatCard-button1", {duration: 5, y: "-400%", opacity: 0, ease: "Expo.easeInOut", delay: 1},'toTeamPage');
        tl4.to("#AlwakalatCard-button2", {duration: 5, y: "-400%", opacity: 0, ease: "Expo.easeInOut", delay: 2},'toTeamPage');
        tl4.to("#team-details-title", {duration: 4, y: "-600%", opacity: 1, ease: "expo.out", delay: -2});
        // tl4.to("#team-list", {duration: 4, y: "-25%", opacity: 1, ease: "expo.out"});
        tl4.from(".team-member", { duration: 6, y: "25%", opacity: 0, stagger: 1, ease: "Expo.easeInOut" });

        // tl4.to(".team-member", {duration: 4, y: "-25%", opacity: 1, ease: "expo.out"});

        var tl5 = gsap.timeline({
            scrollTrigger:{
                trigger: "#ContactUs",
                start: "-60% 10%",
                end: "50% 50%",
                scrub: true,
                sync: true,
                markers: true,
            }
        });
        // tl5.to("#team-list", {duration: 2, y: "-100%", opacity: 0, ease: "expo.Inout"});
        tl5.to(".team-member", { duration: 3, y: "-25%", opacity: 0, stagger: 0.5, ease: "Expo.easeInOut" });
        tl5.to("#get-in-touch-form h1", {duration: 2, opacity: 1, ease: "expo.out"});
        tl5.to("form", {duration: 2, y: "-15%", opacity: 1, ease: "expo.out"});

    }
    else{
        var tl = gsap.timeline({
            scrollTrigger:{
                trigger: "#DownloadApp",
                start: "-85% 10%",
                end: "70% 50%",
                scrub: true,
                sync: true,
                // markers: true,
            }
        });

        tl.to("#Home-title", {duration: 0.5, top: "-20%"},'ace');
        tl.to("#Home-description", {duration: 0.5, top: "-20%"}, 'ace');
        tl.to("#buttons", {duration: 0.5, top: "-20%"}, 'ace');

        tl.to("#center", {duration: 3, right: "110%", y: "65vh", zIndex: 3, scaleY: 0.9, scaleX: 0.9, ease: "Expo.easeInOut"});
        tl.to("#first-left", {opacity: 0,duration: 1.5, left: "70%", right: "30%", ease: "Expo.easeInOut", delay: -3});
        tl.to("#first-right", {opacity: 0,duration: 1.5, right: "70%", ease: "Expo.easeInOut", delay: -3});
        tl.to("#second-left", {opacity: 0,duration: 1.5, left: "110%", ease: "Expo.easeOut", delay: -2});
        tl.to("#second-right", {opacity: 0,duration: 1.5, right: "110%", ease: "Expo.easeOut", delay: -2});

        tl.to("#red-box", {duration: 3, height: "65%", delay: -1.5, ease: "Expo.easeOut"});
        tl.to("#download-details", {duration: 2, y: "-130%", x: "-145%", ease: "Power1.easeOut", delay: -3});


        var tl2 = gsap.timeline({
            scrollTrigger:{
                trigger: "#Project",
                start: "-85% 10%",
                end: "70% 50%",
                scrub: true,
                sync: true,
                // markers: true,
            }
        });

        tl2.to("#center", {duration: 0.3, right: "300%", ease: "Expo.easeInOut"});
        tl2.to("#download-details", {duration: 0.5, left: "200%", opacity: 0, ease: "Expo.easeInOut", delay: -0.3},'ace2');
        tl2.to("#Project-title", {duration: 0.5, left: "7%", opacity: 1, ease: "Expo.easeInOut", delay: -0.7},'ace2');


        var tl8 = gsap.timeline({
            scrollTrigger:{
                trigger: "#Partners",
                start: "-85% 10%",
                end: "70% 50%",
                scrub: true,
                sync: true,
                // markers: true,
            }
        });

        tl8.to("#Partners-title", {duration: 0.5, left: "7%", opacity: 1, ease: "Expo.easeInOut", delay: -0.7},'ace25');


        var tl3 = gsap.timeline({
            scrollTrigger:{
                trigger: "#AlwakalatCard",
                start: "-85% 10%",
                end: "50% 50%",
                scrub: true,
                // sync: true,
                // markers: true,
            }
        });

        tl3.to("#alwakalat-card-details", {duration: 1, left: "8%", opacity: 2, ease: "Expo.easeInOut", delay: -0.3});

        var tl4 = gsap.timeline({
            scrollTrigger:{
                trigger: "#Team",
                start: "-60% 10%",
                end: "52% 50%",
                scrub: true,
                sync: true,
                // markers: true,
            }
        });

        tl4.to("#AlwakalatCard", {duration: 6, left: "10%", opacity: 0, ease: "Expo.easeInOut"});

        tl4.to("#AlwakalatCard h1", {duration: 5, y: "-600%", opacity: 0, ease: "Expo.easeInOut"},'toTeamPage');
        tl4.to("#AlwakalatCard h3", {duration: 5, y: "-400%", opacity: 0, ease: "Expo.easeInOut", delay: 1},'toTeamPage');
        tl4.to("#AlwakalatCard-button1", {duration: 5, y: "-400%", opacity: 0, ease: "Expo.easeInOut", delay: 1},'toTeamPage');
        tl4.to("#AlwakalatCard-button2", {duration: 5, y: "-400%", opacity: 0, ease: "Expo.easeInOut", delay: 2},'toTeamPage');
        tl4.to("#team-details-title", {duration: 4, y: "-650%", opacity: 1, ease: "expo.out"});
        // tl4.to("#team-list", {duration: 4, y: "-20%", opacity: 1, ease: "Expo.easeInOut"});

        // tl4.to(".team-member", {duration: 4, y: "-25%", opacity: 1, ease: "expo.out"});
        tl4.from(".team-member", { duration: 6, y: "25%", opacity: 0, stagger: 1, ease: "Expo.easeInOut" });

        var tl5 = gsap.timeline({
            scrollTrigger:{
                trigger: "#ContactUs",
                start: "-60% 10%",
                end: "50% 50%",
                scrub: true,
                sync: true,
                markers: true,
            }
        });
        // tl5.to("#team-list", {duration: 2, y: "-100%", opacity: 0, ease: "expo.Inout"});
        tl5.to(".team-member", { duration: 6, y: "-25%", opacity: 0, stagger: 1, ease: "Expo.easeInOut" });
        tl5.to("#get-in-touch-form h1", {duration: 2, opacity: 1, ease: "expo.out"});
        tl5.to("form", {duration: 2, y: "-15%", opacity: 1, ease: "Expo.easeOut"});
    }
}


