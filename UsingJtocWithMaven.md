# Introduction #

In order to use Jtoc from a Maven project, one need add this repository to pom.xml's repositories:

```
		<repository>
			<id>Jtoc</id>
			<name>Jtoc Repository</name>
			<url>http://jtoc.googlecode.com/svn/maven2</url>
			<snapshots>
				<enabled>false</enabled>
			</snapshots>
		</repository>
```

And add dependency in the dependencies:
```
		<dependency>
			<groupId>com.google.code.jtoc</groupId>
			<artifactId>jtoc</artifactId>
			<version>0.2.2</version>
		</dependency>
```

# The maven-jtoc-plugin #

A project named "maven-jtoc-plugin" is set up  in order to build Jtoc test project with Maven2, you can check it [here](http://code.google.com/p/maven-jtoc-plugin/).