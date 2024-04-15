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

function submitRating() {
    const rating = document.getElementById("bookRating").value;
    if (!rating) {
        alert("Please select a rating before submitting.");
        return;
    }

    fetch(`/books/${bookId}/rate`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({rating: parseInt(rating)})
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else if (response.status === 400) {
                return response.json().then(data => {
                    throw new Error(Object.values(data).join(", "));
                });
            } else if (response.status === 403) {
                throw new Error("You can only rate books that you have purchased.");
            } else if (response.status === 409) {
                throw new Error("You have already rated this book.");
            } else {
                throw new Error("An unexpected error occurred.")
            }
        })
        .then(data => {
            alert("Your rating has been submitted.");
            document.getElementById('averageRating').textContent = data.averageRating.toFixed(1);
            document.getElementById('ratingsCount').textContent = data.ratingsCount;
        })
        .catch(error => {
            console.error('Error: ', error);
            document.getElementById('errorMessages').textContent = error.message;
        });
}

function openRatingModal() {

    if (isLoggedIn){
        document.getElementById('ratingModal').style.display = 'block';
        openBackdrop();
    } else {
        openLoginModal();
    }

}

function closeRatingModal() {
    document.getElementById('ratingModal').style.display = 'none';
    closeBackdrop();
}

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
            'Content-Type': 'application/json'
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
                <img src="${data.reviewDto.user.pictureBase64}" />
            </div>
            <div class="reviewer-name">${data.reviewDto.user.firstName} ${data.reviewDto.user.lastName}</div>
            <div class="review-rating-title">
                <div class="review-rating">
                    ${generateStars(data.reviewDto.ratingValue)}
                </div>
                <div class="review-title">${data.reviewDto.title}</div>
                <div class="review-date">Reviewed on ${formatDate(data.reviewDto.submissionDate)}</div>
                <div class="review-text">${data.reviewDto.text}</div>
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
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    return date.toLocaleDateString(undefined, options); // Adjust locale as needed
}
