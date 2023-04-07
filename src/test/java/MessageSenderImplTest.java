import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

import static ru.netology.entity.Country.RUSSIA;
import static ru.netology.entity.Country.USA;
import static ru.netology.geo.GeoServiceImpl.*;

public class MessageSenderImplTest {

    static GeoService geoService;
    static LocalizationService localizationService;
    static MessageSender messageSender;

    @BeforeAll
    public static void initSuite() {
        geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(LOCALHOST)).thenReturn(new Location(null, null, null, 0));
        Mockito.when(geoService.byIp(MOSCOW_IP)).thenReturn(new Location("Moscow", RUSSIA, "Lenina", 15));
        Mockito.when(geoService.byIp(NEW_YORK_IP)).thenReturn(new Location("New York", USA, " 10th Avenue", 32));
        Mockito.when(geoService.byIp(Mockito.startsWith("172."))).thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        Mockito.when(geoService.byIp(Mockito.startsWith("96."))).thenReturn(new Location("New York", Country.USA, null, 0));
        Mockito.when(geoService.byCoordinates(Mockito.anyDouble(), Mockito.anyDouble())).thenThrow(new RuntimeException("Not implemented"));
        localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(RUSSIA)).thenReturn("Добро пожаловать");
        Mockito.when(localizationService.locale(USA)).thenReturn("Welcome");
        messageSender = new MessageSenderImpl(geoService, localizationService);
    }

    @Test
    @DisplayName("Test send for RUSSIA IP")
    public void sendRussiaIpTest(TestInfo sendRussiaIpTestInfo) {

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.123.12.19");
        Assertions.assertEquals("Добро пожаловать", messageSender.send(headers),
                sendRussiaIpTestInfo.getDisplayName() + "is NO complete!");
        System.out.println("\n" + sendRussiaIpTestInfo.getDisplayName() + " complete!");
    }

    @Test
    @DisplayName("Test send for USA IP")
    public void sendUsaIpTest(TestInfo sendUsaIpTestInfo) {

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.44.183.149");
        Assertions.assertEquals("Welcome", messageSender.send(headers),
                sendUsaIpTestInfo.getDisplayName() + " is NO complete!");
        System.out.println("\n" + sendUsaIpTestInfo.getDisplayName() + " complete!");
    }
}