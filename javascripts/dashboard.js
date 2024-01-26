// Responsible for highlighting sidebar links based on current page
document.addEventListener('DOMContentLoaded', function () {
    let currentPath = window.location.pathname;
    let options = document.querySelectorAll(".option");
    options.forEach(function (option) {

        const link = option.querySelector("a");
        const image = option.querySelector("img");
        const image_file_name = image.getAttribute("src");
        const image_name = image_file_name.split('.').slice(0,-1).join('.');

        if (link.getAttribute('href') === currentPath) {
            link.style.color = '#AE3236';
            image.setAttribute('src',image_name + "-red.png");
        }
        else
        {
            // Highlight Car Listings when Add Car Page is displayed
            if(currentPath === "/add-car" && link.getAttribute('href') === "/admin/car-listings"){
                link.style.color = '#AE3236';
                image.setAttribute('src',image_name + "-red.png");
            }

            // Highlight Shop Listings when Add Shop Page is displayed
            if(currentPath === "/add-shop" && link.getAttribute('href') === "/admin/shop-listings"){
                link.style.color = '#AE3236';
                image.setAttribute('src',image_name + "-red.png");
            }

            // Highlight Upcoming Appointments when Add Appointment Page is displayed
            if(currentPath === "/add-appointment" && link.getAttribute('href') === "/admin/upcoming-appointments"){
                link.style.color = '#AE3236';
                image.setAttribute('src',image_name + "-red.png");
            }

            // Highlight Upcoming Appointments when View Appointment Page is displayed
            if(currentPath.includes("/view-appointment") && link.getAttribute('href') === "/admin/upcoming-appointments"){
                link.style.color = '#AE3236';
                image.setAttribute('src',image_name + "-red.png");
            }

            // Highlight Upcoming Appointments when Edit Appointment Page is displayed
            if(currentPath.includes("/edit-appointment") && link.getAttribute('href') === "/admin/upcoming-appointments"){
                link.style.color = '#AE3236';
                image.setAttribute('src',image_name + "-red.png");
            }

            // Highlight Car Listings when View Car Page is displayed
            if(currentPath.includes("/view-car") && !currentPath.includes("/view-car-model") && link.getAttribute('href') === "/admin/car-listings"){
                link.style.color = '#AE3236';
                image.setAttribute('src',image_name + "-red.png");
            }

            // Highlight Shop Listings when View Shop Page is displayed
            if(currentPath.includes("/view-shop/") && link.getAttribute('href') === "/admin/shop-listings"){
                link.style.color = '#AE3236';
                image.setAttribute('src',image_name + "-red.png");
            }

            // Highlight Car Listings when Update Price Page is displayed
            if(currentPath.includes("/update-price") && link.getAttribute('href') === "/admin/car-listings"){
                link.style.color = '#AE3236';
                image.setAttribute('src',image_name + "-red.png");
            }

            // Highlight Shop Listings when Edit Shop Page is displayed
            if(currentPath.includes("/edit-shop") && link.getAttribute('href') === "/admin/shop-listings"){
                link.style.color = '#AE3236';
                image.setAttribute('src',image_name + "-red.png");
            }

            // (For Super Admin) Highlight Customers when Customer Details is displayed
            if(currentPath.includes("/view-customer") && link.getAttribute('href') === "/admin/customer-listings"){
                link.style.color = '#AE3236';
                image.setAttribute('src',image_name + "-red.png");
            }

            // (For Super Admin) Highlight Admin when Admin Details is displayed
            if(currentPath.includes("/view-admin") && link.getAttribute('href') === "/admin/admin-listings"){
                link.style.color = '#AE3236';
                image.setAttribute('src',image_name + "-red.png");
            }

            // (For Super Admin) Highlight Admins when Admin Details is displayed
            if(currentPath.includes("/edit-admin") && link.getAttribute('href') === "/admin/admin-listings"){
                link.style.color = '#AE3236';
                image.setAttribute('src',image_name + "-red.png");
            }

            // Highlight Car Models when Add Car Model Page is displayed
            if(currentPath.includes("/add-car-model") && link.getAttribute('href') === "/admin/car-models"){
                link.style.color = '#AE3236';
                image.setAttribute('src',image_name + "-red.png");
            }

            // Highlight Car Models when View Car Model Page is displayed
            if(currentPath.includes("/view-car-model") && link.getAttribute('href') === "/admin/car-models"){
                link.style.color = '#AE3236';
                image.setAttribute('src',image_name + "-red.png");
            }

            // Highlight Car Models when Edit Make Logo Page is displayed
            if(currentPath.includes("/edit-make-logo") && link.getAttribute('href') === "/admin/car-models"){
                link.style.color = '#AE3236';
                image.setAttribute('src',image_name + "-red.png");
            }

            // Highlight Car Models when Add Class Page is displayed
            if(currentPath.includes("/add-class") && link.getAttribute('href') === "/admin/car-models"){
                link.style.color = '#AE3236';
                image.setAttribute('src',image_name + "-red.png");
            }

            // Highlight Car Models when Delete Class Page is displayed
            if(currentPath.includes("/delete-class") && link.getAttribute('href') === "/admin/car-models"){
                link.style.color = '#AE3236';
                image.setAttribute('src',image_name + "-red.png");
            }

        }
    });
});

// For displaying mobile dropdown options drawer
function toggleDrawer() {
    let drawer = document.getElementById("drawer");
    drawer.style.display = (drawer.style.display === "none" || drawer.style.display === "") ? "flex" : "none";
}

function updateLogoPreview() {
    const logoInput = document.getElementById('shop-logo');
    const logoImage = document.getElementById('shop-logo-image');

    const file = logoInput.files[0];

    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            // Set the image source to the data URL
            logoImage.src = e.target.result;
        };
        reader.readAsDataURL(file);
    }
}


//-----------------------------------DISPLAY PAGES FOR SUPER ADMIN ------------------------------------//

function save_new_admin(){
    let admin_name_input = document.getElementById('admin-name').value.trim();
    let admin_email_input = document.getElementById('admin-email').value.trim();
    let admin_role_input = document.getElementById('admin-role').value;
    let csrfToken = document.getElementById('csrfToken').value;
    const email_regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    if(admin_name_input === ''){
        alert("Admin name cannot be empty");
    }

    if(admin_email_input === ''){
        alert("Admin email cannot be empty");
    } else {
        if(!email_regex.test(admin_email_input))
        {
            alert("Invalid Email Format");
        }
    }

    if(admin_role_input === 'Select'){
        alert("Please select a Role")
    }

    if(admin_name_input !== '' && admin_email_input !== '' && admin_role_input !== 'Select'){
        const url = `http://localhost:9000/admin/add-admin?csrfToken=${csrfToken}`;
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                name: admin_name_input,
                emailAddress: admin_email_input,
                role: admin_role_input
            })
        })
            .then(res => {
                if(res.status === 200){
                    alert("Admin added");
                    display_admin_listings();
                }
                else{
                    res.text().then(error => alert(error));
                }
            })
    }
}

function display_add_admin(){
    window.location.href = '/admin/add-admin'
}

function add_new_model(){
    window.location.href = '/admin/add-car-model';
}

function display_car_models(){
    window.location.href = '/admin/car-models';
}

function edit_make_logo(make){
    window.location.href = `/admin/edit-make-logo/${make}`;
}

function display_add_class(make){
    window.location.href = `/admin/add-class/${make}`;
}

function display_delete_class(make){
    window.location.href = `/admin/delete-class/${make}`;
}

function delete_class(make){
    const csrfToken = document.getElementById('csrfToken').value;
    const classToDelete = document.getElementById('class-to-delete').value;

    if(classToDelete === "Select Class")
    {
        alert("Select a class to delete");
    }
    else{
        const url = `http://localhost:9000/admin/delete-class?csrfToken=${csrfToken}`;
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                make: make,
                class: classToDelete
            })
        })
            .then(res => {
                if(res.status === 200){
                    alert("Class: " + classToDelete + " is Deleted From " + make);
                    display_car_model_details(make);
                }
                else{
                    res.text().then(error => alert(error));
                }
            });
    }
}

function display_car_model_details(make){
    window.location.href = `/admin/view-car-model/${make}`;
}

function save_new_class(){
    const csrfToken = document.getElementById('csrfToken').value;
    const model_make_input = document.getElementById('make').value;

    // Save details from previous class container
    const classDiv = document.getElementById('class-container-list');
    const allClasses = classDiv.querySelectorAll('.class-container');

    let flag = true;

    allClasses.forEach(container => {
        const carClass = container.querySelector('.form-field').querySelector('#car-class').value;
        const modelList = container.querySelector('.models-container').querySelector('.models-list');
        const modelInputs = modelList.querySelectorAll('#model_name');
        const inputList = [];

        modelInputs.forEach(input => {
            if(input.value !== "" && carClass !== "")
            {
                inputList.push(input.value)
                selected_classes[carClass] = inputList;
                flag = true;
            }
            else if(carClass === ""){
                alert('Please Enter Class Details');
                flag = false;
            }
            else{
                alert('Please Enter At Least 1 Model Name for Class: ' + carClass);
                flag = false;
            }
        });
    })

    if(flag){
        const url = `http://localhost:9000/admin/add-class?csrfToken=${csrfToken}`;
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                make: model_make_input,
                classes: selected_classes
            })
        })
            .then(res => {
                if(res.status === 200){
                    alert("Class(es) Added to " + model_make_input);
                    display_car_model_details(model_make_input);
                }
                else{
                    res.text().then(error => alert(error));
                }
            })
    }
}

function save_make_logo(make){
    const csrfToken = document.getElementById('csrfToken').value;
    const model_make_logo_input = document.getElementById('shop-logo').files[0];

    let base64String = "";

    function continueWithRequest() {
        const url = `http://localhost:9000/admin/edit-make-logo?csrfToken=${csrfToken}`;
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                make: make,
                logo: base64String.split(',')[1]
            })
        })
            .then(res => {
                if(res.status === 200){
                    alert(make + " Logo Updated");
                    display_car_model_details(make);
                }
                else{
                    res.text().then(error => alert(error));
                }
            });
    }

    if (model_make_logo_input) {
        const reader = new FileReader();
        reader.addEventListener("loadend", (event) => {

            base64String = event.target.result;
            continueWithRequest()

        }, false);
        reader.readAsDataURL(model_make_logo_input);
    } else {
        alert("Please Upload New Make Logo")
    }

}

const selected_models = [];
const selected_classes = {};

function add_class_field(){
    // Save details from previous class container
    const classDiv = document.getElementById('class-container-list');
    const latestClass = classDiv.lastElementChild;
    let flag = true;
    if (latestClass) {
        const carClass = latestClass.querySelector('.form-field').querySelector('#car-class').value;
        const modelList = latestClass.querySelector('.models-container').querySelector('.models-list');
        const modelInputs = modelList.querySelectorAll('#model_name');
        const inputList = [];

        modelInputs.forEach(input => {
            if(input.value !== "" && carClass !== "")
            {
                inputList.push(input.value)
                selected_classes[carClass] = inputList;
                flag = true;
            }
            else if(carClass === ""){
                alert('Please enter class name to create another class');
                flag = false;
            }
            else{
                alert('Please enter at least 1 model name for Class: ' + carClass + ' to create another class');
                flag = false;
            }
        });
    }

    // Only create a new class container when the previous one is filled
    if(flag){
        // Create new class container
        const classContainer = document.createElement('div');
        classContainer.className = 'class-container';

        // Create form field with label and input
        const formField = document.createElement('div');
        formField.className = 'form-field';

        const classLabel = document.createElement('label');
        classLabel.htmlFor = 'car-class';
        classLabel.textContent = 'Car Class';

        const classInput = document.createElement('input');
        classInput.type = 'text';
        classInput.placeholder = 'Enter Car Class';
        classInput.id = 'car-class';

        formField.appendChild(classLabel);
        formField.appendChild(classInput);

        // Create models container with form title and models list
        const modelsContainer = document.createElement('div');
        modelsContainer.className = 'models-container';

        const formTitle = document.createElement('div');
        formTitle.className = 'form-title'

        const modelLabel = document.createElement('label');
        modelLabel.htmlFor = 'models';
        modelLabel.textContent = 'Models';

        const addModelImage = document.createElement('img');
        addModelImage.id = 'add_model';
        addModelImage.src = "/assets/images/plus.png";
        addModelImage.alt = 'Add Model';
        addModelImage.addEventListener('click', function() {
            add_model_field(addModelImage);
        });

        formTitle.appendChild(modelLabel);
        formTitle.appendChild(addModelImage);

        const modelsListDiv = document.createElement('div');
        modelsListDiv.className = 'models-list';

        const modelNameInput = document.createElement('input');
        modelNameInput.type = 'text';
        modelNameInput.id = 'model_name';
        modelNameInput.placeholder = 'Enter Model Name';

        modelsListDiv.appendChild(modelNameInput);
        modelsContainer.appendChild(formTitle);
        modelsContainer.appendChild(modelsListDiv);

        classContainer.appendChild(formField);
        classContainer.appendChild(modelsContainer);

        classDiv.appendChild(classContainer);
    }
}

function add_model_field(clickedElement){
    const classContainer = clickedElement.closest('.class-container');
    const modelsDiv = classContainer.querySelector('.models-list');
    const latestModel = modelsDiv.lastElementChild;

    if (latestModel) {
        const modelName = latestModel.value;

        if(modelName !== "")
        {
            selected_models.push(modelName);
        }
        else{
            alert('Please enter model name to create another model.');
            latestModel.remove();
        }
    }

    const model_name = document.createElement('input');
    model_name.id = 'model_name';
    model_name.placeholder = 'Enter Model Name';
    modelsDiv.appendChild(model_name);
}

function save_new_model(){
    const csrfToken = document.getElementById('csrfToken').value;
    const model_make_input = document.getElementById('make').value;
    const model_make_logo_input = document.getElementById('shop-logo').files[0];

    if(model_make_input === ''){
        alert("Make cannot be empty");
    }

    // Save details from previous class container
    const classDiv = document.getElementById('class-container-list');
    const latestClass = classDiv.lastElementChild;

    if (latestClass) {
        const carClass = latestClass.querySelector('.form-field').querySelector('#car-class').value;
        const modelList = latestClass.querySelector('.models-container').querySelector('.models-list');
        const modelInputs = modelList.querySelectorAll('#model_name');
        const inputList = [];

        modelInputs.forEach(input => {
            if(input.value !== "" && carClass !== "")
            {
                inputList.push(input.value)
                selected_classes[carClass] = inputList;
            }
            else if(carClass === ""){
                alert('Please Enter Class Details');
            }
            else{
                alert('Please Enter At Least 1 Model Name for Class: ' + carClass);
            }
        });
    }

    let base64String = "";

    if (model_make_logo_input) {
        const reader = new FileReader();

        reader.addEventListener("loadend", (event) => {
            base64String = event.target.result;

            const url = `http://localhost:9000/admin/add-car-model?csrfToken=${csrfToken}`;
            fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    make: model_make_input,
                    classes: selected_classes,
                    logo: base64String.split(',')[1]
                })
            })
                .then(res => {
                    if(res.status === 200){
                        alert("Car Model Created");
                        display_car_model_details(model_make_input);
                    }
                    else{
                        res.text().then(error => alert(error));
                    }
                })
        }, false);
        reader.readAsDataURL(model_make_logo_input);
    } else {
        alert("Please Select Make Logo Image");
    }
}

//---------------------------------------------CUSTOMER------------------------------------------------//
function display_customer_details(uid){
    window.location.href = `/admin/view-customer/${uid}`;
}
//---------------------------------------------ADMIN------------------------------------------------//
function display_admin_listings(){
    window.location.href = '/admin/admin-listings';
}
function display_admin_details(uid){
    window.location.href = `/admin/view-admin/${uid}`;
}
function display_edit_admin(uid){
    window.location.href = `/admin/edit-admin/${uid}`;
}

function update_admin(){
    let csrfToken = document.getElementById("csrfToken").value;
    let uid = document.getElementById("admin-uid").value.trim();
    let name = document.getElementById("admin-name").value.trim();
    let email = document.getElementById("admin-email").value.trim();
    let role = document.getElementById("admin-role").value;
    const email_regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    if(uid === ''){
        alert("Admin UID cannot be empty");
    }

    if(name === ''){
        alert("Admin name cannot be empty");
    }

    if(email === ''){
        alert("Admin email cannot be empty");
    } else {
        if(!email_regex.test(email))
        {
            alert("Invalid Email Format");
        }
    }

    if(uid !== '' && name !== '' && email !== ''){
        const url = `http://localhost:9000/admin/edit-admin?csrfToken=${csrfToken}`;
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                uid: uid,
                name: name,
                emailAddress: email,
                role: role
            })
        })
            .then(res => {
                if(res.status === 200){
                    alert("Admin Details Updated");
                    display_admin_details(uid);
                }
                else{
                    res.text().then(error => alert(error));
                }

            })
    }
}

function deactivate_admin(uid){
    let csrfToken = document.getElementById("csrfToken").value;
    const url = `http://localhost:9000/admin/deactivate-admin?csrfToken=${csrfToken}`;
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            uid: uid.toString()
        })
    })
        .then(res => {
            if(res.status === 200){
                alert("Admin Deactivated");
                display_admin_listings();
            }
            else{
                res.text().then(error => alert(error));
            }
        })
}

//-------------------------------------------CHANGE PASSWORD-------------------------------------------//

function update_password(){
    let newPassword = document.getElementById("new-password").value;
    let csrfToken = document.getElementById("csrfToken").value;
    const url = `http://localhost:9000/admin/change-password?csrfToken=${csrfToken}`;
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            password: newPassword
        })
    })
        .then(res => {
            if(res.status === 200){
                alert("Password Updated");
                window.location.href = '/admin/login';
            }
            else{
                res.text().then(error => alert(error));
            }
        })

}

//-------------------------------------------DISPLAY PAGES---------------------------------------------//
function display_home(){
    window.location.href = '/admin/dashboard'
}

//------------------------------------------------CAR-------------------------------------------------//
function display_car_listings() {
    window.location.href = '/admin/car-listings';
}

function display_car_details(listingID){
    window.location.href = `/admin/view-car/${listingID}`;
}

function display_add_car() {
    window.location.href = '/admin/add-car';
}

function display_update_price(listingID){
    window.location.href = `/admin/update-price/${listingID}`;
}

function update_car_price(listingID){
    let csrfToken = document.getElementById("csrfToken").value;
    let price = document.getElementById("new-price").value.trim();

    if(price === ''){
        alert("Please Enter New Price")
    } else {
        const url = `http://localhost:9000/admin/update-price?csrfToken=${csrfToken}`;
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                listingID: listingID,
                price: parseInt(price,10)
            })
        })
            .then(res => {
                if(res.status === 200){
                    alert("Price Updated");
                    display_car_details(listingID);
                }
                else{
                    res.text().then(error => alert(error));
                }
            })
    }
}

function set_as_sold(listingID, price){
    let csrfToken = document.getElementById("csrfToken").value;
    const url = `http://localhost:9000/admin/set-as-sold?csrfToken=${csrfToken}`;
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            listingID: listingID,
            soldPrice: price
        })
    })
        .then(res => {
            if(res.status === 200){
                alert("Car Sold");
                display_car_listings();
            }
            else{
                res.text().then(error => alert(error));
            }
        })
}

function save_new_car(){

    let csrfToken = document.getElementById("csrfToken").value;
    let listingID_input = document.getElementById('listingID').value.trim();
    let make_input = document.getElementById('make').value;
    let model_input = document.getElementById('model').value;
    let subclass_input = document.getElementById('sub-class').value;
    let year_input = document.getElementById('year').value.trim();
    let mileage_input = document.getElementById('mileage').value.trim();
    let price_input = document.getElementById('price').value.trim();
    let geartype_input = document.getElementById('gear-type').value;
    let fueltype_input = document.getElementById('fuel-type').value;
    let bodytype_input = document.getElementById('body-type').value;
    let warranty_input = document.getElementById('warranty').value;
    let warrantyexpiry_input = document.getElementById('warranty-expiry').value.trim();
    let extcolor_input = document.getElementById('exterior-color').value.trim();
    let intcolor_input = document.getElementById('interior-color').value.trim();
    let seattype_input = document.getElementById('seat-type').value;
    let wheeldrive_input = document.getElementById('wheel-drive').value;
    let sunroof_input = document.getElementById('sunroof').value;
    let importcountry_input = document.getElementById('import-country').value.trim();
    // let sellername_input = document.getElementById('seller-name').value;
    // let sellerphone_input = document.getElementById('seller-phone').value.trim();
    let carphotos_input = document.getElementById('upload').files;

    if(year_input === ''){
        alert("Please Enter Car Model Year");
    }

    if(mileage_input === ''){
        alert("Car mileage cannot be empty. Minimum 0km");
    }

    if(price_input === ''){
        alert("Car price cannot be empty.");
    }

    if(geartype_input === 'Gear Type'){
        alert("Please Select Gear Type");
    }

    if(fueltype_input === 'Fuel Type'){
        alert("Please Select Fuel Type");
    }

    if(bodytype_input === 'Body Type'){
        alert("Please Select Body Type");
    }

    if(warranty_input === 'Under Warranty'){
        alert("Please Select Under Warranty (Yes/No)");
    } else {
        warranty_input = (warranty_input === "Yes");
    }

    if(warrantyexpiry_input === ''){
        alert("Please Provide Warranty Expiry Year");
    }

    if(intcolor_input === ''){
        alert("Please Enter Car Interior Color");
    }
    if(extcolor_input === ''){
        alert("Please Enter Car Exterior Color");
    }

    if(seattype_input === 'Seat Type'){
        alert("Please Select Seat Type");
    }

    if(wheeldrive_input === 'Wheel Drive'){
        alert("Please Select Wheel Drive");
    }

    if(sunroof_input === 'Sunroof'){
        alert("Please Select Sunroof Information (Yes/No)");
    }else{
        sunroof_input = (sunroof_input === "Yes");
    }

    if(importcountry_input === 'Import Country'){
        alert("Please Enter Import Country");
    }

    let base64Strings = [];
    if (carphotos_input.length > 0) {
        const promises = [];

        for (const file of carphotos_input) {
            let reader = new FileReader();

            const promise = new Promise((resolve) => {
                reader.addEventListener("load", () => {
                    base64Strings.push(reader.result.split(',')[1]);
                    resolve();
                });
            });

            reader.readAsDataURL(file);
            promises.push(promise);
        }

        Promise.all(promises).then(() => {
            const url = `http://localhost:9000/admin/add-car?csrfToken=${csrfToken}`;
            fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    listingID: listingID_input,
                    make: make_input,
                    model: model_input,
                    subClass: subclass_input,
                    modelYear: parseInt(year_input,10),
                    mileage: parseInt(mileage_input,10),
                    price: parseInt(price_input,10),
                    gearType: geartype_input,
                    fuelType: fueltype_input,
                    bodyType: bodytype_input,
                    underWarranty: warranty_input,
                    warrantyExpiry: parseInt(warrantyexpiry_input,10),
                    exteriorColor: extcolor_input,
                    interiorColor: intcolor_input,
                    seatType: seattype_input,
                    wheelDrive: wheeldrive_input,
                    hasSunRoof: sunroof_input,
                    importCountry: importcountry_input,
                    images: base64Strings
                })
            })
                .then(res => {
                    if (res.status === 200) {
                        alert("Car created");
                        display_car_listings();
                    } else {
                        res.text().then(error => alert(error));
                    }
                });
        });

    } else {
        alert("Add At Least 1 Car Image");
    }
}

//------------------------------------------------SHOP------------------------------------------------//
function display_shop_listings() {
    window.location.href = '/admin/shop-listings';
}
function display_shop_details(uid) {
    window.location.href = `/admin/view-shop/${uid}`;
}
function display_add_shop() {
    window.location.href = '/admin/add-shop';
}
function display_edit_shop(uid) {
    window.location.href = `/admin/edit-shop/${uid}`;
}

function deactivate_shop(uid){
    let csrfToken = document.getElementById("csrfToken").value;
    const url = `http://localhost:9000/admin/delete-shop?csrfToken=${csrfToken}`;
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            uid: uid
        })
    })
        .then(res => {
            if(res.status === 200){
                alert("Shop Deactivated");
                display_shop_listings();
            }
            else{
                res.text().then(error => alert(error));
            }
        })
}

// Add / Delete Account & Discount Information
const selected_accounts = {};
const selected_services = {};
const accounts = ["Select", "Facebook", "Twitter", "Instagram", "Whatsapp", "TikTok", "Snapchat"];

function deleteFields(button,isAccount){
    //Get the parent div
    const parent = button.parentNode;

    //Determine if it is account or discount
    if(isAccount){
        //Get the selected option and remove it from the dictionary
        const selected_option = parent.querySelector("#selected-option").value;
        delete selected_accounts[selected_option];
    }
    else{
        const selected_service = parent.querySelector('#service-name').value;
        delete selected_services[selected_service];
    }

    parent.remove();
}

function addAccountFields() {

    // Check if there is an account div already
    const accountsDiv = document.getElementById('accounts-list');
    const latestAccount = accountsDiv.lastElementChild;

    if(accountsDiv.children.length === (accounts.length - 1))
    {
        alert("No more Social Media available.")
    }
    else{
        if (latestAccount) {

            // Get Account Information (Add last field (unsaved) to the dictionary)
            const acc_name = latestAccount.querySelector('#selected-option').value;
            const acc_link = latestAccount.querySelector('#account-link').value;

            if(acc_link  !== "")
            {
                selected_accounts[acc_name] = acc_link;
            }
            else{
                alert('Please fill the current field(s) to add another account.');
                latestAccount.remove();
            }
        }

        // Re Initialize the select options by removing the previously selected option
        const account_name = document.createElement('select');
        account_name.id = 'selected-option';

        accounts.forEach(option => {
            if(!selected_accounts.hasOwnProperty(option)){
                const optionElement = document.createElement('option');
                optionElement.value = option;
                optionElement.text = option;
                account_name.add(optionElement);
            }
        });

        const div = document.createElement('div');
        div.className = 'account-info'

        const account_link = document.createElement('input');
        account_link.type = 'text';
        account_link.placeholder = 'Enter username/link';
        account_link.id = 'account-link'

        const deletebutton = document.createElement('img');
        deletebutton.src = "/assets/images/remove.png";
        deletebutton.className = 'delete_button';
        deletebutton.onclick = function() {
            deleteFields(deletebutton,true);
        };

        div.appendChild(account_name);
        div.appendChild(account_link);
        div.appendChild(deletebutton);
        accountsDiv.appendChild(div);
    }

}

function addServiceFields() {

    const servicesDiv = document.getElementById('services-list');
    const latestService = servicesDiv.lastElementChild;

    // If yes, get the last div (created last) and get its selected option and value
    if (latestService) {
        const serviceName = latestService.querySelector('#service-name').value;
        const serviceDiscount = latestService.querySelector('#service-discount').value;

        if(serviceName !== "" && serviceDiscount !== "")
        {
            // Add it to the dictionary
            selected_services[serviceName] = parseFloat(serviceDiscount);
        }
        else{
            alert('Please fill the current field(s) to create another discount.');
            latestService.remove();
        }
    }

    const div = document.createElement('div');
    div.className = 'account-info'

    const service_name = document.createElement('input');
    service_name.id = 'service-name';
    service_name.placeholder = 'Name';

    const service_discount = document.createElement('input');
    service_discount.type = 'number';
    service_discount.placeholder = 'Enter Discount (%)';
    service_discount.id = 'service-discount';

    const delete_button = document.createElement('img');
    delete_button.src = "/assets/images/remove.png";
    delete_button.className = 'delete_button';
    delete_button.onclick = function() {
        deleteFields(delete_button,false);
    };

    div.appendChild(service_name);
    div.appendChild(service_discount);
    div.appendChild(delete_button);
    servicesDiv.appendChild(div);
}

function update_shop() {
    const csrfToken = document.getElementById('csrfToken').value;
    const shop_uid = document.getElementById('shop-uid').value;
    const shop_name_input = document.getElementById('shop-name').value.trim();
    const shop_email_input = document.getElementById('shop-email').value.trim();
    const shop_phone_input = document.getElementById('shop-phone').value.trim();
    const shop_address_input = document.getElementById('shop-address').value.trim();
    const shop_logo_input = document.getElementById('shop-logo').files[0];
    const shop_latitude_input = document.getElementById('shop-latitude').value.trim();
    const shop_longitude_input = document.getElementById('shop-longitude').value.trim();

    const email_regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    if(!email_regex.test(shop_email_input))
    {
        alert("Invalid Email Format!");
    }

    if(shop_name_input === ''){
        alert("Shop name cannot be empty");
    }

    if(shop_email_input === ''){
        alert("Shop email cannot be empty");
    }

    if(shop_phone_input === ''){
        alert("Shop phone number cannot be empty");
    }

    if(shop_address_input === ''){
        alert("Shop address cannot be empty");
    }

    if(shop_latitude_input === ''){
        alert("Shop latitude cannot be empty");
    }

    if(shop_longitude_input === ''){
        alert("Shop longitude cannot be empty");
    }

    // Get Discount Information (Add last field (unsaved) to the dictionary)
    const servicesDiv = document.getElementById('services-list');
    const latestService = servicesDiv.children;

    for(const service of latestService){
        const disc_name = service.querySelector('#service-name').value;
        const disc_amount = service.querySelector('#service-discount').value;

        if(disc_name !== "" && disc_amount !== "")
        {
            selected_services[disc_name] = parseFloat(disc_amount);
        }
    }

    // Get Account Information (Add last field (unsaved) to the dictionary)
    const accountsDiv = document.getElementById('accounts-list');
    const latestAccount = accountsDiv.children;

    for (const account of latestAccount){
        const acc_name = account.querySelector('#selected-option').value;
        const acc_link = account.querySelector('#account-link').value;

        if(acc_link  !== "")
        {
            selected_accounts[acc_name] = acc_link;
        }
    }

    let base64String = "";

    if(shop_logo_input){
        const reader = new FileReader();
        reader.addEventListener("load", (event) => {
            base64String = event.target.result;
        }, false);
        reader.readAsDataURL(shop_logo_input);
    } else {
        base64String = document.getElementById("shop-logo-image").src;
    }

    function continueWithRequest() {
        const url = `http://localhost:9000/admin/edit-shop?csrfToken=${csrfToken}`;
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                uid: shop_uid,
                name: shop_name_input,
                email: shop_email_input,
                phone: shop_phone_input,
                address: shop_address_input,
                latitude: parseFloat(shop_latitude_input),
                longitude: parseFloat(shop_longitude_input),
                discountedServices: selected_services,
                socialMediaAccounts: selected_accounts,
                logoURL: base64String.split(',')[1]
            })
        })
            .then(res => {
                if (res.status === 200) {
                    alert("Shop Updated");
                    display_shop_details(shop_uid);
                } else {
                    res.text().then(error => alert(error));
                }
            });
    }

    if (shop_name_input !== "" && shop_email_input !== "" && shop_phone_input !== "" && shop_address_input !== "" && shop_latitude_input !== "" && shop_longitude_input !== "") {
        continueWithRequest();
    }
}

function save_new_shop(){

    // Get Shop Information
    const csrfToken = document.getElementById('csrfToken').value;
    const shop_name_input = document.getElementById('shop-name').value.trim();
    const shop_email_input = document.getElementById('shop-email').value.trim();
    const shop_phone_input = document.getElementById('shop-phone').value.trim();
    const shop_address_input = document.getElementById('shop-address').value.trim();
    const shop_logo_input = document.getElementById('shop-logo').files[0];
    const shop_latitude_input = document.getElementById('shop-latitude').value.trim();
    const shop_longitude_input = document.getElementById('shop-longitude').value.trim();
    const email_regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    if(shop_name_input === ''){
        alert("Shop name cannot be empty");
    }

    if(shop_email_input === ''){
        alert("Shop email cannot be empty");
    } else {
        if(!email_regex.test(shop_email_input))
        {
            alert("Invalid Email Format!");
        }
    }

    if(shop_phone_input === ''){
        alert("Shop phone number cannot be empty");
    }

    if(shop_address_input === ''){
        alert("Shop address cannot be empty");
    }

    if(shop_latitude_input === ''){
        alert("Shop latitude cannot be empty");
    }

    if(shop_longitude_input === ''){
        alert("Shop longitude cannot be empty");
    }

    // Get Discount Information (Add last field (unsaved) to the dictionary)
    const servicesDiv = document.getElementById('services-list');
    const latestService = servicesDiv.lastElementChild;

    if (latestService) {
        const disc_name = latestService.querySelector('#service-name').value;
        const disc_amount = latestService.querySelector('#service-discount').value;

        if(disc_name !== "" && disc_amount !== "")
        {
            selected_services[disc_name] = parseFloat(disc_amount);
        }
    }

    // Get Account Information (Add last field (unsaved) to the dictionary)
    const accountsDiv = document.getElementById('accounts-list');
    const latestAccount = accountsDiv.lastElementChild;

    if (latestAccount) {
        const acc_name = latestAccount.querySelector('#selected-option').value;
        const acc_link = latestAccount.querySelector('#account-link').value;

        if(acc_link  !== "")
        {
            selected_accounts[acc_name] = acc_link;
        }
    }

    if(!shop_logo_input){
        alert("Please upload Shop Logo Image");
    }

    if(Object.keys(selected_services).length === 0){
        alert("Please provide at least 1 service");
    }

    if(Object.keys(selected_accounts).length === 0){
        alert("Please provide at least 1 social media account");
    }

    let base64String = "";

    if (shop_logo_input && shop_name_input !== '' && shop_email_input !== '' && shop_phone_input !== '' && shop_address_input !== '' && shop_latitude_input !== '' && shop_longitude_input !== ''
    && Object.keys(selected_accounts).length > 0 && Object.keys(selected_services).length > 0) {
        const reader = new FileReader();

        reader.addEventListener("loadend", (event) => {
            base64String = event.target.result;

            const url = `http://localhost:9000/admin/add-shop?csrfToken=${csrfToken}`;
            fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    name: shop_name_input,
                    email: shop_email_input,
                    phone: shop_phone_input,
                    address: shop_address_input,
                    latitude: parseFloat(shop_latitude_input),
                    longitude: parseFloat(shop_longitude_input),
                    discountedServices: selected_services,
                    socialMediaAccounts: selected_accounts,
                    logo: base64String.split(',')[1]
                })
            })
                .then(res => {
                    if(res.status === 200){
                        alert("Shop Created");
                        display_shop_listings();
                    }
                    else{
                        res.text().then(error => alert(error));
                    }

                })
        }, false);
        reader.readAsDataURL(shop_logo_input);
    }
}

//-------------------------------------------APPOINTMENTS---------------------------------------------//
function display_appointments(){
    window.location.href = '/admin/upcoming-appointments';
}
function display_appointment_details(){
    window.location.href = '/admin/view-appointment';
}
function display_add_appointment() {
    window.location.href = '/admin/add-appointment';
}
function display_edit_appointment(){
    window.location.href = '/admin/edit-appointment';
}
