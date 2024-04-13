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
            
        })
}