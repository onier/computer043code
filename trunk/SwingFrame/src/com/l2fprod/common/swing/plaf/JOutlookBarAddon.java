/**
 * $ $ License.
 *
 * Copyright $ L2FProd.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.l2fprod.common.swing.plaf;

import com.l2fprod.common.swing.JOutlookBar;
import com.l2fprod.common.swing.border.FourLineBorder;
import com.l2fprod.common.swing.plaf.basic.BasicOutlookButtonUI;
import com.l2fprod.common.swing.plaf.windows.WindowsOutlookBarUI;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

/**
 * JOutlookBarAddon. <br>
 *  
 */
public class JOutlookBarAddon implements ComponentAddon {

  public String getName() {
    return "JOutlookBar";
  }

  public void initialize(LookAndFeelAddons addon) {
    Color barBackground = new Color(167, 166, 170);
    
    // border for the bar and the tab buttons
    Border outlookBarButtonBorder;
    Border outlookBarBorder;
    {
      Color color1 = UIManager.getColor("Button.background").brighter();
      Color color2 = UIManager.getColor("Button.background").darker();

      outlookBarButtonBorder = new WindowsOutlookBarUI.WindowsTabButtonBorder(
      color1, color2);
    outlookBarButtonBorder = new CompoundBorder(outlookBarButtonBorder,
      BorderFactory.createEmptyBorder(3, 3, 3, 3));
    
    	outlookBarBorder = new FourLineBorder(color2, color2, color1, color1);
    }
    
    // border for buttons inside a JOutlookBar
    Border outlookButtonBorder;
    {
      Color color1 = barBackground.brighter();
      Color color2 = barBackground.darker();

      outlookButtonBorder = new BasicOutlookButtonUI.OutlookButtonBorder(
        color1, color2);
      outlookButtonBorder = new CompoundBorder(outlookButtonBorder,
        BorderFactory.createEmptyBorder(3, 3, 3, 3));
    }    

    Object[] defaults = new Object[] {
      JOutlookBar.UI_CLASS_ID,
      WindowsOutlookBarUI.class.getName(),
      "OutlookButtonUI",
      BasicOutlookButtonUI.class.getName(),
      "OutlookBar.background",
      barBackground,
      "OutlookBar.border",
      outlookBarBorder,
      "OutlookBar.tabButtonBorder",
      outlookBarButtonBorder,
      "OutlookButton.border",
      outlookButtonBorder,
      "OutlookBar.tabAlignment",
      new Integer(SwingConstants.CENTER),
    };
    addon.loadDefaults(defaults);
  }

  public void uninitialize(LookAndFeelAddons addon) {
  }

}