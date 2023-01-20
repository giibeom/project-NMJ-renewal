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
import static alex.toy.nmj.fixture.MemberFixture.일반_회원_gibeom;
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
                일반_회원 = 일반_회원_gibeom.엔티티_생성();
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

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 회원_정보_수정_시 {

        @Nested
        @DisplayName("변경할 컬럼 값이 주어지면")
        class Context_with_change_data {

            private Member 기존_회원_정보 = 일반_회원_gibeom.엔티티_생성();
            private Member 변경할_회원_정보 = Member.builder()
                    .name("이름 바꿀꺼지롱")
                    .phone("010-0000-0000")
                    .build();

            @Test
            @DisplayName("값이 있는 컬럼만 수정된다")
            void it_change_not_blank() throws Exception {

                기존_회원_정보.update(변경할_회원_정보);

                assertThat(기존_회원_정보.getPassword()).isEqualTo(기존_회원_정보.getPassword());
                assertThat(기존_회원_정보.getName()).isEqualTo(변경할_회원_정보.getName());
                assertThat(기존_회원_정보.getPhone()).isEqualTo(변경할_회원_정보.getPhone());
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 회원_정보_삭제_시 {

        private Member 기존_회원_정보 = 일반_회원_gibeom.엔티티_생성();

        @Test
        @DisplayName("회원 상태값이 DELETED로 수정된다")
        void it_change_member_status_deleted() throws Exception {
            기존_회원_정보.delete();

            assertThat(기존_회원_정보.isDeleted()).isTrue();
        }
    }
}
