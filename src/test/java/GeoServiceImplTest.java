import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;

import static ru.netology.entity.Country.RUSSIA;
import static ru.netology.entity.Country.USA;
import static ru.netology.geo.GeoServiceImpl.*;

public class GeoServiceImplTest {

    static GeoService geoService;

    @BeforeAll
    public static void initSuite() {
        geoService = Mockito.mock(GeoServiceImpl.class);

        Mockito.when(geoService.byIp(LOCALHOST)).thenReturn(new Location(null, null, null, 0));
        Mockito.when(geoService.byIp(MOSCOW_IP)).thenReturn(new Location("Moscow", RUSSIA, "Lenina", 15));
        Mockito.when(geoService.byIp(NEW_YORK_IP)).thenReturn(new Location("New York", USA, " 10th Avenue", 32));
        Mockito.when(geoService.byIp(Mockito.startsWith("172."))).thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        Mockito.when(geoService.byIp(Mockito.startsWith("96."))).thenReturn(new Location("New York", Country.USA, null, 0));

        Mockito.when(geoService.byCoordinates(Mockito.anyDouble(), Mockito.anyDouble())).thenThrow(new RuntimeException("Not implemented"));

    }

    @AfterAll
    public static void completeSuite() {
    }

    @Test
    @DisplayName("Test IP")
        public void byIpLocalhostTest(TestInfo byIpLocalhostTestInfo) {
        Assertions.assertNull(geoService.byIp("127.0.0.1").getCountry(), byIpLocalhostTestInfo.getDisplayName() + " no complete");
        System.out.println(byIpLocalhostTestInfo.getDisplayName() + " complete");
    }

    @ParameterizedTest
    @DisplayName("Test IP for russian ")
    @ValueSource(strings = {"172.0.32.11", "172.1.22.33", "172.8.78.45"})
        public void byIpRussiaTest(String ip) {
        Assertions.assertEquals(RUSSIA, geoService.byIp(ip).getCountry());
        System.out.println("Test IP " + ip + " complete");
    }

    @ParameterizedTest
    @DisplayName("Test IP for USA")
    @ValueSource(strings = {"96.44.183.149", "96.11.222.333", "96.11.165.987"})
    public void byIpUsaTest(String ip) {
        Assertions.assertEquals(USA, geoService.byIp(ip).getCountry());
        System.out.println("Test IP " + ip + " complete");
    }

    @Test
    @DisplayName("Test ")
    public void byCoordinatesTest(TestInfo byCoordinatesTestInfo) {
        Assertions.assertThrows(RuntimeException.class, () -> geoService.byCoordinates(124.56, 45.78),
                byCoordinatesTestInfo.getDisplayName() + " is NO complete");
        System.out.println(byCoordinatesTestInfo.getDisplayName() + " complete");
    }
}