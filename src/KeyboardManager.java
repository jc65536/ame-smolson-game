import java.util.*;
import java.awt.event.*;
import java.awt.*;

public class KeyboardManager implements KeyEventDispatcher {

    static Map<Integer, Boolean> keyMap = new HashMap<>();

    public boolean keyDown(int key) {
        var r = keyMap.get(key);
        return r == null ? false : r;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        switch (e.getID()) {
            case KeyEvent.KEY_PRESSED:
                keyMap.put(e.getKeyCode(), true);
                break;
            case KeyEvent.KEY_RELEASED:
                keyMap.put(e.getKeyCode(), false);
                break;
        }
        return false;
    }
    
}
