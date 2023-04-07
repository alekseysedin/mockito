import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import static ru.netology.entity.Country.RUSSIA;
import static ru.netology.entity.Country.USA;

public class LocalizationServiceImplTest {

    static LocalizationService localizationService;

    @BeforeAll
    public static void initSuite() {

        localizationService = Mockito.mock(LocalizationServiceImpl.class);

        Mockito.when(localizationService.locale(RUSSIA)).thenReturn("Добро пожаловать");
        Mockito.when(localizationService.locale(USA)).thenReturn("Welcome");

    }

    @Test
    @DisplayName("Test location for RUSSIA")
    public void localeRussiaTest(TestInfo localeTestInfo) {
        Assertions.assertEquals(localizationService.locale(RUSSIA), "Добро пожаловать",
                localeTestInfo.getDisplayName() + " is NO complete");
        System.out.println(localeTestInfo.getDisplayName() + " complete");
    }

    @Test
    @DisplayName("Test location for USA")
    public void localeUsaTest(TestInfo localeTestInfo) {
        Assertions.assertEquals(localizationService.locale(USA), "Welcome",
                localeTestInfo.getDisplayName() + " is NO complete");
        System.out.println(localeTestInfo.getDisplayName() + " complete");
    }
}