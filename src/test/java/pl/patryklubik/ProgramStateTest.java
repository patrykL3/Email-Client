package pl.patryklubik;

import pl.patryklubik.controller.presistance.PersistenceAccess;
import pl.patryklubik.controller.presistance.ValidAccount;
import pl.patryklubik.view.ViewFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;


/**
 * Create by Patryk Łubik on 17.01.2021.
 */

public class ProgramStateTest {

    @Mock
    private PersistenceAccess persistenceAccessMock;
    @Mock
    private ViewFactory viewFactoryMock;

    ProgramState programState;

    @BeforeEach
    public void setUp(){
        openMocks(this);

        programState = new ProgramState(persistenceAccessMock, viewFactoryMock);
    }

    @Test
    public void testValidPersistence(){
        when(persistenceAccessMock.loadFromPersistence()).thenReturn(new ArrayList<ValidAccount>());
        programState.init();
        verify(viewFactoryMock, times(1)).showMainWindow();
    }

    @Test
    public void testMissingPersistence(){
        when(persistenceAccessMock.loadFromPersistence()).thenReturn(null);
        programState.init();
        verify(viewFactoryMock, times(1)).showLoginWindow();
    }
}
