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
        body: JSON.stringify({rating: rating})
    })
        .then(response => {
            if (response.ok) {
                return response.json();
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
            alert(error.message);
        });
}

function openRatingModal() {
    document.getElementById('ratingModal').style.display = 'block';
}

function closeRatingModal() {
    document.getElementById('ratingModal').style.display = 'none';
}