package drlugha.user_app.repository;

import drlugha.user_app.entity.Feedback;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
