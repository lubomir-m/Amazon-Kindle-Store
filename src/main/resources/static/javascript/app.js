
const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const csrfHeaderName = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');


function openLoginModal() {
        document.getElementById('loginModal').style.display = 'block';
        openBackdrop();
}

function closeLoginModal() {
    document.getElementById('loginModal').style.display = 'none';
    closeBackdrop();
}

//Close Login Modal
document.addEventListener('DOMContentLoaded', function () {
    let button = document.getElementById('loginModalCloseButton');
    button.addEventListener('click', closeLoginModal);
})

function openBackdrop() {
    document.getElementById('modalBackdrop').style.display = 'block';
}

function closeBackdrop() {
    document.getElementById('modalBackdrop').style.display = 'none';
}

// Open Rating Modal
document.addEventListener('DOMContentLoaded', function () {
    let button = document.querySelector('.open-rating-modal-btn');
    if (button) {
        button.addEventListener('click', function () {
            if (!isLoggedIn) {
                openLoginModal();
                return;
            }
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
                    document.getElementById('resultMessagesRating').textContent = '';
                    document.getElementById('errorMessagesRating').textContent = '';
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

// Open Review Modal
document.addEventListener('DOMContentLoaded', function () {
    let button = document.querySelector('.open-review-modal-btn');
    button.addEventListener('click', function () {
        if (!isLoggedIn) {
            openLoginModal();
            return
        }

        let bookId = this.getAttribute('data-book-id');
        const resultText = document.getElementById('resultMessagesReview');
        const errorText = document.getElementById('errorMessagesReview');

        fetch(`/books/${bookId}/check-review`)
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => {
                        throw new Error(text);
                    });
                }
                return response.text();
            })
            .then(() => {
                resultText.textContent = '';
                errorText.textContent = '';
                document.getElementById('reviewModal').style.display = 'block';
                openBackdrop();
            })
            .catch(error => {
                document.getElementById('commonModalText').textContent = '';
                document.getElementById('commonModalErrors').textContent = error.message;
                openCommonModal();
            });
    });
});

//Review Modal: Close, Submit buttons
document.addEventListener('DOMContentLoaded', function () {
    const closeButton = document.getElementById('close-review-btn');
    const submitButton = document.getElementById('submit-review-btn');
    const modal = document.getElementById('reviewModal');
    const resultText = document.getElementById('resultMessagesReview');
    const errorText = document.getElementById('errorMessagesReview');

    closeButton.addEventListener('click', function () {
        modal.style.display = 'none';
        closeBackdrop();
    });

    submitButton.addEventListener('click', function () {
        let reviewTitle = document.getElementById('reviewTitle').value;
        let reviewText = document.getElementById('reviewText').value;
        let reviewRating = document.getElementById('reviewRating').value;
        let bookId = this.getAttribute('data-book-id');

        if (!reviewTitle || !reviewText || reviewRating === '') {
            errorText.textContent = 'Please fill out all required fields.';
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
                    return response.text().then(text => {
                        throw new Error(text);
                    });
                }
                return response.json();
            })
            .then(data => {
                updateReviewUI(data);
                errorText.textContent = ''
                resultText.textContent = 'Your review has been submitted successfully.';
            })
            .catch(error => {
                resultText.textContent = '';
                errorText.textContent = error.message;
            });
    });
});

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

// Close Common Modal
document.addEventListener('DOMContentLoaded', function () {
    const closeButton = document.getElementById('commonModalCloseButton');
    if (closeButton) {
        closeButton.addEventListener('click', closeCommonModal);
    }
});


// Buy Now Buttons
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

// Add to Cart Button
document.addEventListener('DOMContentLoaded', function () {
    let button = document.querySelector('.add-to-cart-btn');
    const modalText = document.getElementById('commonModalText');
    const modalErrors = document.getElementById('commonModalErrors');

    button.addEventListener('click', function () {
        if (!isLoggedIn) {
            openLoginModal();
            return;
        }

        let bookId = button.getAttribute('data-book-id');
        fetch(`/books/${bookId}/cart`, {
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
                modalText.textContent = 'The book was added to your shopping cart.';
                document.getElementById('numberOfBooksInCart').textContent = result.text;
                openCommonModal();
            })
            .catch(error => {
                modalText.textContent = '';
                modalErrors.textContent = error.message;
                openCommonModal();
            });
    });
});

// Add to Your List Button
document.addEventListener('DOMContentLoaded', function () {
    let button = document.querySelector('.add-to-your-list-btn');
    const modalText = document.getElementById('commonModalText');
    const modalErrors = document.getElementById('commonModalErrors');

    button.addEventListener('click', function () {
        if (!isLoggedIn) {
            openLoginModal();
            return;
        }

        let bookId = button.getAttribute('data-book-id');
        fetch(`/books/${bookId}/list`, {
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

// Update and Delete Review Buttons
document.addEventListener('DOMContentLoaded', function() {
    const updateButtons = document.querySelectorAll('.update-review-btn');
    updateButtons.forEach(button => {
        button.addEventListener('click', function() {
            const bookId = this.getAttribute('data-book-id');
            const rating = this.getAttribute('data-rating');
            const title = this.getAttribute('data-title');
            const text = this.getAttribute('data-text');

            document.getElementById('reviewRating').value = rating;
            document.getElementById('reviewTitle').value = title;
            document.getElementById('reviewText').value = text;

            const updateButton = document.getElementById('update-review-btn');
            updateButton.setAttribute('data-book-id', bookId);

            document.getElementById('errorMessagesReview').textContent = '';
            document.getElementById('resultMessagesReview').textContent = '';
            document.getElementById('reviewModal').style.display = 'block';
            openBackdrop();
        });
    });


    //TODO: mark the review as deleted on the page
    const deleteButtons = document.querySelectorAll('.delete-review-btn');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function () {
            const bookId = this.getAttribute('data-book-id');
            const modalText = document.getElementById('commonModalText');
            const modalErrors = document.getElementById('commonModalErrors');

            fetch(`/books/${bookId}/review`, {
                method: 'DELETE',
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

    const updateButton = document.getElementById('update-review-btn');
    updateButton.addEventListener('click', function () {
        const bookId = this.getAttribute('data-book-id');
        const reviewRating = document.getElementById('reviewRating').value;
        const reviewTitle = document.getElementById('reviewTitle').value;
        const reviewText = document.getElementById('reviewText').value;

        if (!reviewTitle || !reviewText || reviewRating === '') {
            document.getElementById('errorMessagesReview').textContent = 'Please fill out all required fields.';
            return;
        }
        const reviewData = {reviewRating, reviewTitle, reviewText};

        fetch(`/books/${bookId}/review/`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeaderName]: csrfToken
            },
            body: JSON.stringify(reviewData)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Unable to update review due to an error.");
                }

                document.getElementById('errorMessagesReview').textContent = '';
                document.getElementById('resultMessagesReview').textContent =
                    'The review was updated successfully.';
            })
            .catch(error => {
                document.getElementById('resultMessagesReview').textContent = '';
                document.getElementById('errorMessagesReview').textContent = error.message;
            });
    });
});

// Update and Delete Rating Buttons
document.addEventListener('DOMContentLoaded', function() {
    const updateButtons = document.querySelectorAll('.update-rating-btn');
    updateButtons.forEach(button => {
        button.addEventListener('click', function() {
            const bookId = this.getAttribute('data-book-id');
            const rating = this.getAttribute('data-rating');

            document.getElementById('bookRating').value = rating;

            const updateButton = document.getElementById('update-rating-btn');
            updateButton.setAttribute('data-book-id', bookId);

            document.getElementById('errorMessagesRating').textContent = '';
            document.getElementById('resultMessagesRating').textContent = '';
            document.getElementById('ratingModal').style.display = 'block';
            openBackdrop();
        });
    });

    //TODO: mark rating as deleted on the page
    const deleteButtons = document.querySelectorAll('.delete-rating-btn');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function () {
            const bookId = this.getAttribute('data-book-id');
            const modalText = document.getElementById('commonModalText');
            const modalErrors = document.getElementById('commonModalErrors');

            fetch(`/books/${bookId}/rate`, {
                method: 'DELETE',
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

    const updateButton = document.getElementById('update-rating-btn');
    updateButton.addEventListener('click', function () {
        const bookId = this.getAttribute('data-book-id');
        const rating = document.getElementById('bookRating').value;

        if (rating === '') {
            document.getElementById('errorMessagesRating').textContent = 'Please select a rating.';
            return;
        }
        const reviewData = {rating};

        fetch(`/books/${bookId}/rate/`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeaderName]: csrfToken
            },
            body: JSON.stringify(reviewData)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Unable to update rating due to an error.");
                }

                document.getElementById('errorMessagesRating').textContent = '';
                document.getElementById('resultMessagesRating').textContent =
                    'The rating was updated successfully.';
            })
            .catch(error => {
                document.getElementById('resultMessagesRating').textContent = '';
                document.getElementById('errorMessagesRating').textContent = error.message;
            });
    });
});


// Checkout Button
document.addEventListener('DOMContentLoaded', function () {
    const checkoutButton = document.querySelector('.checkout-btn');
    const commonModalCloseButton = document.getElementById('commonModalCloseButton');
    let checkoutSuccess = false;

    checkoutButton.addEventListener('click', function () {
        const modalText = document.getElementById('commonModalText');
        const modalErrors = document.getElementById('commonModalErrors');

        fetch(`/users/cart/checkout` , {
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
                checkoutSuccess = true;
                openCommonModal();
            })
            .catch(error => {
                modalText.textContent = '';
                modalErrors.textContent = error.message;
                checkoutSuccess = false;
                openCommonModal();
            });
    });

    commonModalCloseButton.addEventListener('click', function () {
        if (checkoutSuccess) {
            window.location.href = '/users/books';
        }
    });
});


// Delete buttons for the cart and wishlist

