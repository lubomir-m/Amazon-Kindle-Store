
const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const csrfHeaderName = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');


function openLoginModal() {
    if (!isLoggedIn) {
        document.getElementById('loginModal').style.display = 'block';
        openBackdrop();
    }

}
function closeLoginModal() {
    document.getElementById('loginModal').style.display = 'none';
    closeBackdrop();

}

function openBackdrop() {
    document.getElementById('modalBackdrop').style.display = 'block';
}

function closeBackdrop() {
    document.getElementById('modalBackdrop').style.display = 'none';
}

// Rate this eBook
document.addEventListener('DOMContentLoaded', function () {
    let button = document.querySelector('.open-rating-modal-btn');
    if (button) {
        button.addEventListener('click', function () {
            let bookId = this.getAttribute('data-book-id');

            fetch(`/books/${bookId}/check-rating`)
                .then(response => {
                    return response.text().then(text => {
                        return {text: text, ok: response.ok};
                    });
                })
                .then(result => {
                    if (!result.ok) {
                        throw new Error(result.text);
                    }
                    document.getElementById('ratingModal').style.display = 'block';
                    openBackdrop();
                })
                .catch(error => {
                    document.getElementById('commonModalText').textContent = '';
                    document.getElementById('commonModalErrors').textContent = error.message;
                    openCommonModal();
                });
        });
    }
});

// Rating Modal: Close, Submit buttons
document.addEventListener('DOMContentLoaded', function () {
    let closeButton = document.getElementById('close-rating-btn');
    let submitButton = document.getElementById('submit-rating-btn');
    const modal = document.getElementById('ratingModal');
    const resultText = document.getElementById('resultMessagesRating');
    const errorText = document.getElementById('errorMessagesRating');

    closeButton.addEventListener('click', function () {
        modal.style.display = 'none';
        closeBackdrop();
    });

    submitButton.addEventListener('click', function () {
        let rating = document.getElementById('bookRating').value;
        if (rating === '') {
            errorText.textContent = 'Please select a rating.';
            return;
        }

        let ratingData = {rating: rating};
        let bookId = this.getAttribute('data-book-id');
        fetch(`/books/${bookId}/rate`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeaderName]: csrfToken
            },
            body: JSON.stringify(ratingData)
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => {
                        throw new Error(text);
                    });
                }
                return response.json();
            })
            .then(data => {
                errorText.textContent = '';
                resultText.textContent = 'Your rating has been submitted successfully.';
                document.getElementById('averageRating').textContent = data.avererageRating.toFixed(1);
                document.getElementById('ratingsCount').textContent = data.ratingsCount;
            })
            .catch(error => {
                resultText.textContent = '';
                errorText.textContent = error.message;
            });
    });
});


function openReviewModal() {
    if (isLoggedIn) {
        document.getElementById('reviewModal').style.display = 'block';
        openBackdrop();
    } else {
        openLoginModal();
    }
}

function closeReviewModal() {
    document.getElementById('reviewModal').style.display = 'none';
    closeBackdrop();
}

function submitReview() {
    let reviewTitle = document.getElementById('reviewTitle').value;
    let reviewText = document.getElementById('reviewText').value;
    let reviewRating = document.getElementById('reviewRating').value;

    if (!reviewTitle || !reviewText || reviewRating === "") {
        document.getElementById('errorMessagesReview').innerText = 'All fields have to be filled out.';
        return;
    }

    let reviewData = {
        reviewTitle: reviewTitle,
        reviewText: reviewText,
        reviewRating: parseInt(reviewRating)
    }

    fetch(`/books/${bookId}/review`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeaderName]: csrfToken
        },
        body: JSON.stringify(reviewData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to submit review.');
            }
            return response.json();
        })
        .then(data => {
            // console.log(data);
            alert("Your review has been submitted.");
            updateReviewUI(data);
        })
        .catch(error => {
            document.getElementById('errorMessagesReview').innerText = error.message;
        });
}

function updateReviewUI(data) {
    document.getElementById('averageRating').textContent = data.averageRating.toFixed(1);
    document.getElementById('ratingsCount').textContent = `${data.ratingsCount} ratings`;

    let newReview = document.createElement('div');
    newReview.classList.add('review');

    newReview.innerHTML = `
        <div class="review-author">
            <div class="picture-container">
                <img src="${data.pictureBase64}"  alt="reviewer-picture"/>
            </div>
            <div class="reviewer-name">${data.firstName} ${data.lastName}</div>
            <div class="review-rating-title">
                <div class="review-rating">
                    ${generateStars(data.ratingValue)}
                </div>
                <div class="review-title">${data.title}</div>
                <div class="review-date">Reviewed on ${formatDate(data.submissionDate)}</div>
                <div class="review-text">${data.text}</div>
            </div>
        </div>
    `;


    let reviewList = document.querySelector('.review-list');
    reviewList.removeChild(reviewList.lastChild);
    reviewList.insertBefore(newReview, reviewList.firstChild);
}

function generateStars(ratingValue) {
    let starsHtml = '';
    for (let i = 1; i <= ratingValue; i++) {
        starsHtml += '<svg class="star full-star"><use href="#star-icon"></use></svg>';
    }
    for (let i = ratingValue + 1; i <= 5; i++) {
        starsHtml += '<svg class="star empty-star"><use href="#star-icon"></use></svg>';
    }
    return starsHtml;
}

function formatDate(dateStr) {
    const date = new Date(dateStr);
    const options = {year: 'numeric', month: 'long', day: 'numeric'};
    return date.toLocaleDateString(undefined, options);
}

//TODO: check // Login Form
document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', function (event) {
            event.preventDefault();
            const formData = new FormData(this);
            const email = formData.get('email');
            const password = formData.get('password');

            fetch('/users/login/perform_login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    [csrfHeaderName]: csrfToken
                },
                body: `email=${encodeURIComponent(email)}&password=${encodeURIComponent(password)}`
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error("Login failed.");
                    }
                    return response.text();
                })
                .then(() => {
                    window.location.href = '/';
                })
                .catch(error => {
                    document.getElementById('loginFormErrors').innerText = error.message;
                    document.getElementById('loginFormErrors').style.display = 'block';
                });
        });
    }
});

//TODO: check // Registration Form
function registerUser() {
    const email = document.getElementById('email').value;
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const firstName = document.getElementById('firstName').value;
    const lastName = document.getElementById('lastName').value;
    const age = parseInt(document.getElementById('age').value);


    if (!email || !username || !password || password !== confirmPassword ||
        !firstName || !lastName || isNaN(age)) {
        document.getElementById('errorMessagesRegistration').textContent = 'Please fill in all fields correctly.';
        return;
    }

    const userData = {
        email: email,
        username: username,
        password: password,
        confirmPassword: confirmPassword,
        firstName: firstName,
        lastName: lastName,
        age: age
    };

    fetch('/users/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            [csrfHeaderName]: csrfToken
        },
        body: JSON.stringify(userData)
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(data => {
                    throw new Error(Object.values(data).join(", "));
                });
            }
            return response.json();
        })
        .then(data => {
            alert('You have registered successfully.');
            window.location.href = '/users/login';
        })
        .catch(error => {
            document.getElementById('errorMessagesRegistration').textContent = error.message;
        });
}

function openCommonModal() {
    if (isLoggedIn) {
        document.getElementById('commonModal').style.display = 'block';
        openBackdrop();
    }
}

function closeCommonModal() {
    document.getElementById('commonModal').style.display = 'none';
    closeBackdrop();
}

document.addEventListener('DOMContentLoaded', function () {
    const closeButton = document.getElementById('commonModalCloseButton');
    if (closeButton) {
        closeButton.addEventListener('click', closeCommonModal);
    }
});


document.addEventListener('DOMContentLoaded', function () {
    const buttons = document.querySelectorAll('.buy-now-btn');
    const modalText = document.getElementById('commonModalText');
    const modalErrors = document.getElementById('commonModalErrors');


    buttons.forEach(button => {
        button.addEventListener('click', function () {
            if (!isLoggedIn) {
                openLoginModal();
                return;
            }

            const bookId = this.getAttribute('data-book-id');
            fetch(`/books/${bookId}/purchase`, {
                method: 'POST',
                headers: {
                    [csrfHeaderName]: csrfToken
                }
            })
                .then(response => {
                    return response.text().then(text => {
                        return {text: text, ok: response.ok};
                    });
                })
                .then(result => {
                    if (!result.ok) {
                        throw new Error(result.text);
                    }
                    modalErrors.textContent = '';
                    modalText.textContent = result.text;
                    openCommonModal();
                })
                .catch(error => {
                    modalText.textContent = '';
                    modalErrors.textContent = error.message;
                    openCommonModal();
                });
        });
    });
});


