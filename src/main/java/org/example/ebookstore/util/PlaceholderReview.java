package org.example.ebookstore.util;

import org.example.ebookstore.entities.Review;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlaceholderReview {
    private static final List<Review> placeholderReviews = new ArrayList<>();

    static {
        // array size 10
        String[] reviewTexts = {
                "A tapestry of intricate storytelling, this book weaves together themes of courage, betrayal, and redemption in a way that is both profound and profoundly human. It's a rare gem that holds up a mirror to the soul, challenging and comforting in equal measure.",
                "This narrative is a masterclass in character development, with each chapter peeling back layers of intrigue and complexity. The author's deft prose and sharp dialogue create a world so tangible, it feels as if you could step into it. A truly immersive experience.",
                "From the relentless pull of the opening line to the satisfying weight of the final sentence, this book is a triumphant exploration of the human condition. It manages to be both wildly imaginative and deeply relatable, a narrative alchemy that's as rare as it is captivating.",
                "With an unflinching voice and a keen eye for the poetic in the everyday, this book delivers a story that resonates with the urgency of modern life. It's a poignant reminder of the ties that bind us across divides, told with a tenderness that is all too real.",
                "This novel is a beautiful paradox, brimming with moments of stark reality and ethereal fantasy. The journey it takes you on is one of introspection and discovery, with a narrative rhythm that beats like a heart full of stories yet to be told.",
                "The author has crafted a world rich with detail, yet it's the characters who steal the show. They are flawed, fierce, and unforgettable. Their victories and defeats echo long after the last page is turned, leaving a lingering promise of more tales to come.",
                "Equal parts harrowing and hopeful, this book doesn't just tell a story—it sings a saga. The intertwining plots are like threads in a grand tapestry, each one vibrant with color and meaning. It's a sweeping epic that manages to be both timeless and timely.",
                "This novel takes the road less traveled, with a narrative that twists and turns through uncharted territories of the mind and heart. It's a deep dive into the unknown waters of storytelling, where each chapter is a stroke that takes you closer to the breathtaking depths.",
                "A blend of suspense and sentiment, this book grips you from the start, refusing to let go as it plunges into the complexities of love, loss, and the quest for truth. It's a literary odyssey that promises—and delivers—a journey for the ages.",
                "The story unfolds with the precision of a clockmaker, each piece slotting into place with satisfying clarity. Yet, it's the clock's face—depicting the nuanced expressions of its characters—that will keep you contemplating the nature of time and legacy long after the final tick."
        };


        for (String text : reviewTexts) {
            placeholderReviews.add(createPlaceholderReview(text));
        }
    }

    public static List<Review> getPlaceholderReviews() {
        List<Review> result = new ArrayList<>(placeholderReviews);
        Collections.shuffle(result);
        return result;
    }

    private static Review createPlaceholderReview(String text) {
        // Create a Review object and set its text property
        Review review = new Review();
        review.setText(text);
        // If you have other properties to set for the Review object, set them here
        // For example: review.setSubmissionDate(LocalDate.now());
        return review;
    }
}
