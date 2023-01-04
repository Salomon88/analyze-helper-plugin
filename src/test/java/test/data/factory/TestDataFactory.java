//package test.data.factory;
//
//import kotlin.sequences.Sequence;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//import org.jetbrains.teamcity.rest.*;
//
//import java.io.File;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.time.Duration;
//import java.time.ZonedDateTime;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//
//public class TestDataFactory {
//
//    public static Build getBuild() {
//        return new Build() {
//            @NotNull
//            @Override
//            public BuildId getId() {
//                return new BuildId("phpBuild");
//            }
//
//            @NotNull
//            @Override
//            public BuildConfigurationId getBuildConfigurationId() {
//                return new BuildConfigurationId("phpConfBuild");
//            }
//
//            @Nullable
//            @Override
//            public String getBuildNumber() {
//                return "127.0.0.1";
//            }
//
//            @Nullable
//            @Override
//            public BuildStatus getStatus() {
//                return BuildStatus.FAILURE;
//            }
//
//            @NotNull
//            @Override
//            public Branch getBranch() {
//                return new Branch() {
//                    @Nullable
//                    @Override
//                    public String getName() {
//                        return "feature";
//                    }
//
//                    @Override
//                    public boolean isDefault() {
//                        return false;
//                    }
//                };
//            }
//
//            @NotNull
//            @Override
//            public BuildState getState() {
//                return BuildState.FINISHED;
//            }
//
//            @Override
//            public boolean getPersonal() {
//                return false;
//            }
//
//            @NotNull
//            @Override
//            public String getName() {
//                return "Special feature";
//            }
//
//            @Nullable
//            @Override
//            public BuildCanceledInfo getCanceledInfo() {
//                return null;
//            }
//
//            @Nullable
//            @Override
//            public BuildCommentInfo getComment() {
//                return null;
//            }
//
//            @Nullable
//            @Override
//            public Boolean getComposite() {
//                return null;
//            }
//
//            @NotNull
//            @Override
//            public String getHomeUrl() {
//                return "http//:www.salamon.ru";
//            }
//
//            @Nullable
//            @Override
//            public String getStatusText() {
//                return null;
//            }
//
//            @NotNull
//            @Override
//            public ZonedDateTime getQueuedDateTime() {
//                return ZonedDateTime.now();
//            }
//
//            @Nullable
//            @Override
//            public ZonedDateTime getStartDateTime() {
//                return null;
//            }
//
//            @Nullable
//            @Override
//            public ZonedDateTime getFinishDateTime() {
//                return null;
//            }
//
//            @Nullable
//            @Override
//            public BuildRunningInfo getRunningInfo() {
//                return null;
//            }
//
//            @NotNull
//            @Override
//            public List<Parameter> getParameters() {
//                return Collections.emptyList();
//            }
//
//            @NotNull
//            @Override
//            public List<String> getTags() {
//                return Collections.emptyList();
//            }
//
//            @NotNull
//            @Override
//            public List<Revision> getRevisions() {
//                return Collections.emptyList();
//            }
//
//            @NotNull
//            @Override
//            public List<Change> getChanges() {
//                return Collections.emptyList();
//            }
//
//            @NotNull
//            @Override
//            public List<Build> getSnapshotDependencies() {
//                return Collections.emptyList();
//            }
//
//            @Nullable
//            @Override
//            public PinInfo getPinInfo() {
//                return null;
//            }
//
//            @Nullable
//            @Override
//            public TriggeredInfo getTriggeredInfo() {
//                return null;
//            }
//
//            @Nullable
//            @Override
//            public BuildAgent getAgent() {
//                return null;
//            }
//
//            @Override
//            public boolean getDetachedFromAgent() {
//                return false;
//            }
//
//            @NotNull
//            @Override
//            public Sequence<TestOccurrence> tests(@Nullable TestStatus testStatus) {
//                new TestOccurrence() {
//                    @NotNull
//                    @Override
//                    public TestOccurrenceId getTestOccurrenceId() {
//                        return null;
//                    }
//
//                    @NotNull
//                    @Override
//                    public TestId getTestId() {
//                        return null;
//                    }
//
//                    @NotNull
//                    @Override
//                    public TestStatus getStatus() {
//                        return null;
//                    }
//
//                    @Override
//                    public boolean getNewFailure() {
//                        return false;
//                    }
//
//                    @NotNull
//                    @Override
//                    public String getName() {
//                        return null;
//                    }
//
//                    @Override
//                    public boolean getMuted() {
//                        return false;
//                    }
//
//                    @Nullable
//                    @Override
//                    public List<String> getMetadataValues() {
//                        return null;
//                    }
//
//                    @Override
//                    public boolean getIgnored() {
//                        return false;
//                    }
//
//                    @Nullable
//                    @Override
//                    public BuildId getFixedIn() {
//                        return null;
//                    }
//
//                    @Nullable
//                    @Override
//                    public BuildId getFirstFailedIn() {
//                        return null;
//                    }
//
//                    @NotNull
//                    @Override
//                    public Duration getDuration() {
//                        return null;
//                    }
//
//                    @NotNull
//                    @Override
//                    public String getDetails() {
//                        return null;
//                    }
//
//                    @Override
//                    public boolean getCurrentlyMuted() {
//                        return false;
//                    }
//
//                    @NotNull
//                    @Override
//                    public BuildId getBuildId() {
//                        return null;
//                    }
//                };
//                return ;
//            }
//
//            @NotNull
//            @Override
//            public Sequence<TestRun> testRuns(@Nullable TestStatus testStatus) {
//                return null;
//            }
//
//            @NotNull
//            @Override
//            public Sequence<BuildProblemOccurrence> getBuildProblems() {
//                return null;
//            }
//
//            @Override
//            public void addTag(@NotNull String s) {
//
//            }
//
//            @Override
//            public void setComment(@NotNull String s) {
//
//            }
//
//            @Override
//            public void replaceTags(@NotNull List<String> list) {
//
//            }
//
//            @Override
//            public void pin(@NotNull String s) {
//
//            }
//
//            @Override
//            public void unpin(@NotNull String s) {
//
//            }
//
//            @NotNull
//            @Override
//            public List<BuildArtifact> getArtifacts(@NotNull String s, boolean b, boolean b1) {
//                return null;
//            }
//
//            @NotNull
//            @Override
//            public BuildArtifact findArtifact(@NotNull String s, @NotNull String s1) {
//                return null;
//            }
//
//            @NotNull
//            @Override
//            public BuildArtifact findArtifact(@NotNull String s, @NotNull String s1, boolean b) {
//                return null;
//            }
//
//            @Override
//            public void downloadArtifacts(@NotNull String s, @NotNull File file) {
//
//            }
//
//            @Override
//            public void downloadArtifact(@NotNull String s, @NotNull OutputStream outputStream) {
//
//            }
//
//            @Override
//            public void downloadArtifact(@NotNull String s, @NotNull File file) {
//
//            }
//
//            @NotNull
//            @Override
//            public InputStream openArtifactInputStream(@NotNull String s) {
//                return null;
//            }
//
//            @Override
//            public void downloadBuildLog(@NotNull File file) {
//
//            }
//
//            @Override
//            public void cancel(@NotNull String s, boolean b) {
//
//            }
//
//            @NotNull
//            @Override
//            public List<Parameter> getResultingParameters() {
//                return null;
//            }
//
//            @Override
//            public void finish() {
//
//            }
//
//            @NotNull
//            @Override
//            public String getWebUrl() {
//                return null;
//            }
//
//            @Nullable
//            @Override
//            public String fetchStatusText() {
//                return null;
//            }
//
//            @NotNull
//            @Override
//            public Date fetchQueuedDate() {
//                return null;
//            }
//
//            @Nullable
//            @Override
//            public Date fetchStartDate() {
//                return null;
//            }
//
//            @Nullable
//            @Override
//            public Date fetchFinishDate() {
//                return null;
//            }
//
//            @NotNull
//            @Override
//            public List<Parameter> fetchParameters() {
//                return null;
//            }
//
//            @NotNull
//            @Override
//            public List<Revision> fetchRevisions() {
//                return null;
//            }
//
//            @NotNull
//            @Override
//            public List<Change> fetchChanges() {
//                return null;
//            }
//
//            @Nullable
//            @Override
//            public PinInfo fetchPinInfo() {
//                return null;
//            }
//
//            @Nullable
//            @Override
//            public TriggeredInfo fetchTriggeredInfo() {
//                return null;
//            }
//
//            @NotNull
//            @Override
//            public BuildConfigurationId getBuildTypeId() {
//                return null;
//            }
//
//            @NotNull
//            @Override
//            public Date getQueuedDate() {
//                return null;
//            }
//
//            @Nullable
//            @Override
//            public Date getStartDate() {
//                return null;
//            }
//
//            @Nullable
//            @Override
//            public Date getFinishDate() {
//                return null;
//            }
//        };
//    }
//}
