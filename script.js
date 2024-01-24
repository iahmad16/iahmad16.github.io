// function scrollToPage(sectionID){
//     console.log(sectionID);
//     var section = document.getElementById(sectionID);
//     section.scrollIntoView({behavior: "smooth", block: "start", inline: "nearest"});
// }

function scrollToPage(id) {
    const element = document.getElementById(id);
    const start = window.pageYOffset;
    const target = element.offsetTop;
    const distance = target - start;
    const startTime = performance.now();

    function scrollStep(timestamp) {
        const progress = Math.min((timestamp - startTime) / 1000, 1);
        window.scrollTo(0, start + distance * progress);

        if (progress < 1) {
            requestAnimationFrame(scrollStep);
        }
    }

    requestAnimationFrame(scrollStep);
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


var tl = gsap.timeline({
    scrollTrigger:{
    trigger: "#DownloadApp",
    start: "-85% 10%",
    end: "70% 50%",
    scrub: true,
    sync: true,
    markers: true,
    }
});

tl.to("h1", {duration: 0.5, top: "-20%"},'ace');
tl.to("h3", {duration: 0.5, top: "-20%"}, 'ace');
tl.to("#buttons", {duration: 0.5, top: "-20%"}, 'ace');

tl.to("#center", {duration: 3, right: "110%", y: "80%", zIndex: 3, ease: "Expo.easeInOut"});
tl.to("#first-left", {opacity: 0,duration: 1.5, left: "70%", right: "30%", ease: "Expo.easeInOut", delay: -3});
tl.to("#first-right", {opacity: 0,duration: 1.5, right: "70%", ease: "Expo.easeInOut", delay: -3});
tl.to("#second-left", {opacity: 0,duration: 1.5, left: "110%", ease: "Expo.easeOut", delay: -2});
tl.to("#second-right", {opacity: 0,duration: 1.5, right: "110%", ease: "Expo.easeOut", delay: -2});

tl.to("#red-box", {duration: 3, height: "70%", delay: -1.5, ease: "Expo.easeOut"});
tl.to("#download-details", {duration: 2, y: "-130%", x: "-145%", ease: "Power1.easeOut", delay: -3});


var tl2 = gsap.timeline({
    scrollTrigger:{
    trigger: "#Project",
    start: "-85% 10%",
    end: "100% 50%",
    scrub: true,
    // sync: true,
    markers: true,
    }
});

tl2.to("#center", {duration: 0.3, right: "300%", ease: "Expo.easeInOut"});
tl2.to("#download-details", {duration: 0.5, left: "200%", opacity: 0, ease: "Expo.easeInOut", delay: -0.3},'ace2');

var tl3 = gsap.timeline({
    scrollTrigger:{
    trigger: "#AlwakalatCard",
    start: "-85% 10%",
    end: "100% 50%",
    scrub: true,
    // sync: true,
    markers: true,
    }
});

tl3.to("#alwakalat-card-details", {duration: 1, left: "20%", opacity: 2, ease: "Expo.easeInOut", delay: -0.3});
