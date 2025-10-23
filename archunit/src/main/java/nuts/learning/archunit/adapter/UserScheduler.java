package nuts.learning.archunit.adapter;

import lombok.RequiredArgsConstructor;
import nuts.learning.archunit.application.provided.UserRegister;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
public class UserScheduler {

    private final UserRegister register;

    @Scheduled(fixedRate = 5000)  // 5초마다 실행
    public void processUsers() {
        System.out.println("정기적으로 사용자 처리...");
        // 필요한 비즈니스 로직
    }
}
