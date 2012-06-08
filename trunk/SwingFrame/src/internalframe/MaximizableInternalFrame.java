package internalframe;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.swing.Box;
import javax.swing.Box.Filler;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicInternalFrameUI;

public class MaximizableInternalFrame extends JInternalFrame {

    private boolean isXP;
    private JFrame mainFrame;
    private JMenuBar mainMenuBar;
    private String mainTitle;
    private JComponent titlePane;
    private Border normalBorder;
    private PropertyChangeListener pcl;
    private static Object WP_MINBUTTON;
    private static Object WP_RESTOREBUTTON;
    private static Object WP_CLOSEBUTTON;
    private static Object WP_MDIMINBUTTON;
    private static Object WP_MDIRESTOREBUTTON;
    private static Object WP_MDICLOSEBUTTON;
    private Method setButtonIcons;
    private Method enableActions;

    public MaximizableInternalFrame() {
        init();
    }

    public MaximizableInternalFrame(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4) {
        super(paramString, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4);
        init();
    }

    private void init() {
        this.normalBorder = getBorder();
        this.isXP = this.normalBorder.getClass().getName().endsWith("XPBorder");
        if (this.isXP) {
            setRootPaneCheckingEnabled(false);
            this.titlePane = ((BasicInternalFrameUI) getUI()).getNorthPane();

            if (this.pcl == null) {
                this.pcl = new PropertyChangeListener() {

                    public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
                        String str = paramPropertyChangeEvent.getPropertyName();
                        if ((str.equals("icon")) || (str.equals("maximum")) || (str.equals("closed"))) {
                            MaximizableInternalFrame.this.updateFrame();
                        }
                    }
                };
                addPropertyChangeListener(this.pcl);
            }
        } else if (this.pcl != null) {
            removePropertyChangeListener(this.pcl);
            this.pcl = null;
        }
    }

    private void updateFrame() {
        JFrame localJFrame;
        if ((!this.isXP) || ((localJFrame = getMainFrame()) == null)) {
            return;
        }
        JMenuBar localJMenuBar = getMainMenuBar();
        BasicInternalFrameUI localBasicInternalFrameUI = (BasicInternalFrameUI) getUI();
        if ((isMaximum()) && (!isIcon()) && (!isClosed())) {
            if (localBasicInternalFrameUI.getNorthPane() != null) {
                this.mainTitle = localJFrame.getTitle();
                localJFrame.setTitle(this.mainTitle + " - " + getTitle());
                if (localJMenuBar != null) {
                    updateButtonStates();
                    localJMenuBar.add(Box.createGlue());
                    for (Component localComponent : this.titlePane.getComponents()) {
                        if ((localComponent instanceof JButton)) {
                            localJMenuBar.add(localComponent);
                        } else {
                            if (!(localComponent instanceof JLabel)) {
                                continue;
                            }
                            localJMenuBar.add(Box.createHorizontalStrut(3), 0);
                            localJMenuBar.add(localComponent, 1);
                            localJMenuBar.add(Box.createHorizontalStrut(3), 2);
                        }
                    }
                    localBasicInternalFrameUI.setNorthPane(null);
                    setBorder(null);
                }
            }
        } else if (localBasicInternalFrameUI.getNorthPane() == null) {
            localJFrame.setTitle(this.mainTitle);
            if (localJMenuBar != null) {
                for (Component localComponent : localJMenuBar.getComponents()) {
                    if (((localComponent instanceof JButton)) || ((localComponent instanceof JLabel))) {
                        this.titlePane.add(localComponent);
                    } else if ((localComponent instanceof Box.Filler)) {
                        localJMenuBar.remove(localComponent);
                    }
                }
                localJMenuBar.repaint();
                updateButtonStates();
                localBasicInternalFrameUI.setNorthPane(this.titlePane);
                setBorder(this.normalBorder);
            }
        }
    }

    public void updateUI() {
        int i = (this.isXP) && (getBorder() == null) ? 1 : 0;
        if (i != 0) {
            try {
                setMaximum(false);
            } catch (PropertyVetoException localPropertyVetoException1) {
            }
        }
        super.updateUI();
        init();
        if (i != 0) {
            try {
                setMaximum(true);
            } catch (PropertyVetoException localPropertyVetoException2) {
            }
        }
    }

    private JFrame getMainFrame() {
        if (this.mainFrame == null) {
            JDesktopPane localJDesktopPane = getDesktopPane();
            if (localJDesktopPane != null) {
                this.mainFrame = ((JFrame) SwingUtilities.getWindowAncestor(localJDesktopPane));
            }
        }
        return this.mainFrame;
    }

    private JMenuBar getMainMenuBar() {
        if (this.mainMenuBar == null) {
            JFrame localJFrame = getMainFrame();
            if (localJFrame != null) {
                this.mainMenuBar = localJFrame.getJMenuBar();
                if ((this.mainMenuBar != null) && (!(this.mainMenuBar.getLayout() instanceof FixedMenuBarLayout))) {
                    this.mainMenuBar.setLayout(new FixedMenuBarLayout(this.mainMenuBar, 0));
                }
            }
        }

        return this.mainMenuBar;
    }

    public void setTitle(String paramString) {
        if ((this.isXP) && (isMaximum())
                && (getMainFrame() != null)) {
            getMainFrame().setTitle(this.mainTitle + " - " + paramString);
        }

        super.setTitle(paramString);
    }

    private void updateButtonStates() {
        try {
            if (this.setButtonIcons == null) {
                Class localClass1 = this.titlePane.getClass();
                Class localClass2 = localClass1.getSuperclass();
                this.setButtonIcons = localClass1.getDeclaredMethod("setButtonIcons", new Class[0]);
                this.enableActions = localClass2.getDeclaredMethod("enableActions", new Class[0]);
                this.setButtonIcons.setAccessible(true);
                this.enableActions.setAccessible(true);
            }
            this.setButtonIcons.invoke(this.titlePane, new Object[0]);
            this.enableActions.invoke(this.titlePane, new Object[0]);
        } catch (Exception localException) {
        }
    }

    static {

        try {
            Class localClass = Class.forName("com.sun.java.swing.plaf.windows.TMSchema$Part");

            if (localClass != null) {
                WP_MINBUTTON = localClass.getField("WP_MINBUTTON").get(null);
                WP_RESTOREBUTTON = localClass.getField("WP_RESTOREBUTTON").get(null);
                WP_CLOSEBUTTON = localClass.getField("WP_CLOSEBUTTON").get(null);
                WP_MDIMINBUTTON = localClass.getField("WP_MDIMINBUTTON").get(null);
                WP_MDIRESTOREBUTTON = localClass.getField("WP_MDIRESTOREBUTTON").get(null);
                WP_MDICLOSEBUTTON = localClass.getField("WP_MDICLOSEBUTTON").get(null);
            }

            for (String str1 : new String[]{"maximize", "minimize", "iconify", "close"}) {
                String str2 = "InternalFrame." + str1 + "Icon";
                UIManager.put(str2, new MDIButtonIcon(UIManager.getIcon(str2)));
            }
        } catch (ClassNotFoundException localClassNotFoundException) {
        } catch (NoSuchFieldException localNoSuchFieldException) {
        } catch (IllegalAccessException localIllegalAccessException) {
        }

    }

    private static class MDIButtonIcon
            implements Icon {

        Icon windowsIcon;
        Field part;

        MDIButtonIcon(Icon paramIcon) {
            this.windowsIcon = paramIcon;

            if (MaximizableInternalFrame.WP_MINBUTTON != null) {
                try {
                    this.part = this.windowsIcon.getClass().getDeclaredField("part");
                    this.part.setAccessible(true);
                } catch (NoSuchFieldException localNoSuchFieldException) {
                    localNoSuchFieldException.printStackTrace();
                }
            }
        }

        public void paintIcon(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2) {
            if (this.part != null) {
                try {
                    Object localObject = this.part.get(this.windowsIcon);

                    if ((paramComponent.getParent() instanceof JMenuBar)) {
                        if (localObject == MaximizableInternalFrame.WP_MINBUTTON) {
                            this.part.set(this.windowsIcon, MaximizableInternalFrame.WP_MDIMINBUTTON);
                        } else if (localObject == MaximizableInternalFrame.WP_RESTOREBUTTON) {
                            this.part.set(this.windowsIcon, MaximizableInternalFrame.WP_MDIRESTOREBUTTON);
                        } else if (localObject == MaximizableInternalFrame.WP_CLOSEBUTTON) {
                            this.part.set(this.windowsIcon, MaximizableInternalFrame.WP_MDICLOSEBUTTON);
                        }

                    } else if (localObject == MaximizableInternalFrame.WP_MDIMINBUTTON) {
                        this.part.set(this.windowsIcon, MaximizableInternalFrame.WP_MINBUTTON);
                    } else if (localObject == MaximizableInternalFrame.WP_MDIRESTOREBUTTON) {
                        this.part.set(this.windowsIcon, MaximizableInternalFrame.WP_RESTOREBUTTON);
                    } else if (localObject == MaximizableInternalFrame.WP_MDICLOSEBUTTON) {
                        this.part.set(this.windowsIcon, MaximizableInternalFrame.WP_CLOSEBUTTON);
                    }
                } catch (IllegalAccessException localIllegalAccessException) {
                    localIllegalAccessException.printStackTrace();
                }
            }
            this.windowsIcon.paintIcon(paramComponent, paramGraphics, paramInt1, paramInt2);
        }

        public int getIconWidth() {
            return this.windowsIcon.getIconWidth();
        }

        public int getIconHeight() {
            return this.windowsIcon.getIconHeight();
        }
    }

    private class FixedMenuBarLayout extends BoxLayout {

        public FixedMenuBarLayout(Container paramInt, int i) {
            super(paramInt, i);
        }

        public void layoutContainer(Container paramContainer) {
            super.layoutContainer(paramContainer);

            for (Component localComponent : paramContainer.getComponents()) {
                if ((localComponent instanceof JButton)) {
                    int k = (paramContainer.getHeight() - localComponent.getHeight()) / 2;
                    localComponent.setLocation(localComponent.getX(), Math.max(2, k));
                }
            }
        }
    }
}