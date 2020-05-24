package io.micronaut.starter.core.test

import io.micronaut.starter.application.ApplicationType
import io.micronaut.starter.test.ApplicationTypeCombinations
import io.micronaut.starter.test.CommandSpec
import io.micronaut.starter.options.BuildTool
import io.micronaut.starter.options.Language
import io.micronaut.starter.options.TestFramework
import spock.lang.Unroll

class CreateAwsLambdaGraalvmSpec extends CommandSpec {

    @Unroll
    void 'create-#applicationType with features aws-lambda, graalvm and lang #lang and build #build and test framework: #testFramework'(ApplicationType applicationType, Language lang, BuildTool build, TestFramework testFramework

    ) {
        given:
        List<String> features = ['aws-lambda', 'graalvm']
        generateProject(lang, build, features, applicationType, testFramework)

        when:
        build == BuildTool.GRADLE ? executeGradleCommand('test') : executeMavenCommand("test")

        then:
        testOutputContains("BUILD SUCCESS")

        where:
        [applicationType, lang, build, testFramework] << ApplicationTypeCombinations.combinations([ApplicationType.DEFAULT, ApplicationType.FUNCTION])
    }

    @Override
    String getTempDirectoryPrefix() {
        "test-awslambdagraalvm"
    }
}
