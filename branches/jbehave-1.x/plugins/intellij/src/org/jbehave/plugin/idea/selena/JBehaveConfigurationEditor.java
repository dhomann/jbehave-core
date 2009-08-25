package org.jbehave.plugin.idea.selena;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import com.intellij.execution.junit.SourceScope;
import com.intellij.execution.junit2.configuration.ClassBrowser;
import com.intellij.ide.util.TreeClassChooser;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;

public class JBehaveConfigurationEditor extends SettingsEditor<JBehaveConfiguration>
{
  private Project project;

  private TextFieldWithBrowseButton behaviorClassInput;
  private JComboBox moduleComponent;
  private JComponent editorUi;

  public JBehaveConfigurationEditor(Project project) {
    this.project = project;
    initComponents();
  }

  private void initComponents() {
    initClassChooser();
    initModuleComboBox();
    editorUi = createEditorUi();
  }

  private void initClassChooser() {
    behaviorClassInput = new TextFieldWithBrowseButton();
    behaviorClassInput.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        BehaviorClassBrowser browser = new BehaviorClassBrowser(project, "Choose Behavior");
        browser.setField(behaviorClassInput);
        behaviorClassInput.setText(browser.show());
      }
    });
  }

  private void initModuleComboBox() {
    moduleComponent = new JComboBox(ModuleManager.getInstance(project).getSortedModules());
    moduleComponent.setRenderer(new DefaultListCellRenderer()
    {
      public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value != null) {
          Module module = (Module) value;
          label.setText(module.getName());
          label.setIcon(module.getModuleType().getNodeIcon(false));
        }
        return label;
      }
    });
    moduleComponent.setSelectedIndex(0);
  }

  private JComponent createEditorUi() {
    JPanel panel = new JPanel();
    panel.setLayout(new GridBagLayout());
    panel.add(behaviorClassInput, new GridBagConstraints(
        0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(4, 4, 0, 4), 0, 0
    ));
    panel.add(new Label("Use classpath and JDK of module:"), new GridBagConstraints(
        0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
        new Insets(4, 4, 0, 4), 0, 0
    ));
    panel.add(moduleComponent, new GridBagConstraints(
        0, 2, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
        new Insets(4, 4, 0, 4), 0, 0
    ));
    panel.add(new JPanel(), new GridBagConstraints(
        0, 5, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(0, 0, 0, 0), 0, 0
    ));
    return panel;
  }

  protected void resetEditorFrom(JBehaveConfiguration configuration) {
    ConfigurationData data = configuration.getData();
    behaviorClassInput.setText(data.getBehaviorClass());
    moduleComponent.setSelectedItem(data.getModule(project));
  }

  protected void applyEditorTo(JBehaveConfiguration configuration) throws ConfigurationException {
    ConfigurationData data = configuration.getData();
    data.setBehaviorClass(behaviorClassInput.getText());
    configuration.setModule((Module) moduleComponent.getSelectedItem());
  }

  @NotNull
  protected JComponent createEditor() {
    return editorUi;
  }

  protected void disposeEditor() {
  }

  private static class BehaviorClassBrowser extends ClassBrowser
  {
    public BehaviorClassBrowser(Project project, String name) {
      super(project, name);
    }

    protected TreeClassChooser.ClassFilterWithScope getFilter() throws NoFilterException {
      return new TreeClassChooser.ClassFilterWithScope()
      {
        public GlobalSearchScope getScope() {
          return SourceScope.wholeProject(getProject()).getGlobalSearchScope();
        }

        public boolean isAccepted(PsiClass aClass) {
          return true;
        }
      };
    }

    protected PsiClass findClass(String name) {
      return null;
    }

    public String show() {
      return super.showDialog();
    }
  }
}