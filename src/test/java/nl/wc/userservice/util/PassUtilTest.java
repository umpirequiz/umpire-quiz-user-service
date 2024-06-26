package nl.wc.userservice.util;

import nl.wc.userservice.exceptions.NoSuchAlgorithmRuntimeException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class PassUtilTest {

    @InjectMocks
    private PassUtil sut = new PassUtil();

    @Test
    void noSuchAlgorithException() {
        sut.setAlgorithm("nope");
        assertThat(sut.getAlgorithm()).isEqualTo("nope");

        assertThatThrownBy(() -> sut.digest("peter", "peter"))
                .isInstanceOf(NoSuchAlgorithmRuntimeException.class);

    }
}