package com.mathworks.ci;

import java.util.Collection;
import java.util.LinkedList;
import jetbrains.buildServer.serverSide.InvalidProperty;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import jetbrains.buildServer.serverSide.RunType;
import jetbrains.buildServer.serverSide.RunTypeRegistry;
import jetbrains.buildServer.util.PropertiesUtil;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class MatlabBuildRunner  extends RunType {

  private final PluginDescriptor descriptor;

  public MatlabBuildRunner(RunTypeRegistry registry, PluginDescriptor descriptor){
    registry.registerRunType(this);
    this.descriptor = descriptor;
  }

  @NotNull
  @Override
  public String getType() {
    return MatlabConstants.BUILD_RUNNER_TYPE;
  }

  @NotNull
  @Override
  public String getDisplayName() {
    return MatlabConstants.BUILD_RUNNER_NAME;
  }

  @NotNull
  @Override
  public String getDescription() {
    return MatlabConstants.BUILD_RUNNER_DESCRIPTION;
  }

  @Nullable
  @Override
  public PropertiesProcessor getRunnerPropertiesProcessor() {
        return new PropertiesProcessor() {
            @Override
            public Collection<InvalidProperty> process(final Map<String, String> properties) {
                Collection<InvalidProperty> invalid = new LinkedList<InvalidProperty>();
                if (PropertiesUtil.isEmptyOrNull(properties.get("MatlabPathKey"))) {
                    invalid.add(new InvalidProperty("MatlabPathKey", "MATLAB root cannot be empty"));
                }
                return invalid;
            }
        };
  }

  @Nullable
  @Override
  public String getEditRunnerParamsJspFilePath() {
    return descriptor.getPluginResourcesPath("runMatlabBuildEditView.jsp");
  }

  @Nullable
  @Override
  public String getViewRunnerParamsJspFilePath() {
    return descriptor.getPluginResourcesPath("runMatlabBuildPropertiesView.jsp");
  }

  @Nullable
  @Override
  public Map<String, String> getDefaultRunnerProperties() {
    return new HashMap<>();
  }
}
