package nl.wc.userservice.util;

import nl.wc.userservice.exceptions.TokenException;
import nl.wc.userservice.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class TokenUtilTest {

    // @Mock
    // private Logger loggerMock;

    @InjectMocks
    private TokenUtil sut;

    @Test
    void issueTokenUserIsNullThrowsTokenException() {
        User u = null;
        // doNothing()
        //         .when(loggerMock)
        //         .info(anyString());

        assertThatThrownBy(() -> sut.issueToken(u))
                .isInstanceOf(TokenException.class)
                .hasMessage("User or username is null");

        // verify(loggerMock, times(1))
        //         .info(anyString());
    }
}
