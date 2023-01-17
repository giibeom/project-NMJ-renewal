package alex.toy.nmj.domain;

import alex.toy.nmj.member.domain.Member;
import alex.toy.nmj.member.domain.MemberStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static alex.toy.nmj.fixture.MemberFixture.관리자_회원_기범;
import static alex.toy.nmj.fixture.MemberFixture.매장_회원_Alex;
import static alex.toy.nmj.fixture.MemberFixture.일반_회원_giibeom;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Member 테스트")
class MemberTest {

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 회원_인스턴스_생성_시 {

        @Nested
        @DisplayName("일반 회원일 경우")
        class Context_with_user_type {

            private Member 일반_회원;

            @BeforeEach
            void setUp() {
                일반_회원 = 일반_회원_giibeom.엔티티_생성();
            }

            @Test
            @DisplayName("회원 상태는 NORMAL이다")
            void it_status_normal() throws Exception {
                assertThat(일반_회원.getStatus()).isEqualTo(MemberStatus.NORMAL);
            }
        }

        @Nested
        @DisplayName("매장 회원일 경우")
        class Context_with_store_type {

            private Member 매장_회원;

            @BeforeEach
            void setUp() {
                매장_회원 = 매장_회원_Alex.엔티티_생성();
            }

            @Test
            @DisplayName("회원 상태는 WAIT이다")
            void it_status_wait() throws Exception {
                assertThat(매장_회원.getStatus()).isEqualTo(MemberStatus.WAIT);
            }
        }

        @Nested
        @DisplayName("관리자 회원일 경우")
        class Context_with_admin_type {

            private Member 관리자_회원;

            @BeforeEach
            void setUp() {
                관리자_회원 = 관리자_회원_기범.엔티티_생성();
            }

            @Test
            @DisplayName("회원 상태는 NORMAL이다")
            void it_status_normal() throws Exception {
                assertThat(관리자_회원.getStatus()).isEqualTo(MemberStatus.NORMAL);
            }
        }
    }
}
