Animoto API Java Client
=======================

The Animoto API is a RESTful web service that transforms images, videos,
music, and text into amazing video presentations.

The Animoto API Java Client provides a convenient Java interface for working
with the Animoto RESTful HTTP API.

### Topics

* [Who should read this document](#who_should_read_this_document)
* [What is covered in this document](#what_is_covered_in_this_document)
* [Getting Started using the Java Client](#getting_started_using_the_java_client)
* [How to contribute to this client](#how_to_contribute)

For for detailed Java client documentation, see the 
[java client javadocs][javadoc]

<a name="who_should_read_this_document"></a>
## Who should read this document?

This document is primarily aimed at developers looking to integrate with
Animoto services from a Java environment or using the Java language.

<a name="what_is_covered_in_this_document"></a>
## What is covered in this document

This document covers the technical details of the Animoto API Java client and 
provides a general overview of its use.

This document does not cover the details of the Animoto API itself. For such
information please see the [Animoto API documentation][api_docs]

<a name="getting_started_using_the_ruby_client"></a>
## Getting Started using the Ruby Client

### Requirements

The Animoto API Client requires Java 1.5. It is highly recommended that you
have Maven 2.2 or greater installed as well since all of the builds and test
scripts work off of Maven.

To download or learn more about Maven:

http://maven.apache.org

You must also have a valid Animoto Platform credential set to use the library.

### Dependencies

The Animoto API Client uses the following libraries:

* Apache Commons Bean Utils (http://commons.apache.org/beanutils)
* Apache Http Client 4.x (http://hc.apache.org/httpcomponents-client)
* Google Gson (http://code.google.com/p/google-gson)
* Apache Commons CLI (http://commons.apache.org/cli)

For testing, the following is used to verify JSON results:

* JSON Simple (http://code.google.com/p/json-simple)

All versions and dependencies are declared in the Maven project object model.

### Build Commands

The project uses the basic Maven tasks: 

* `mvn clean` - Clean your project directory.
* `mvn compile` - Compile the library.
* `mvn test` - Run all unit tests for the library.
* `mvn package` - Create a JAR file of the library.
* `mvn javadoc:javadoc` - Generate Javadoc documentation locally.
* `mvn assembly:assembly` - Generate one uber-JAR with all dependencies in the JAR. This is useful for running the CLI interface.

### Creating a video using the Java client

This example shows you how to create a video in one shot using a 
`DirectingAndRenderingJob`.

    // Set up the api client
    ApiClient apiClient = ApiClientFactory.newInstance();

    // Build a directing manifest with all the visuals and audio for the 
    // video.
    DirectingManifest directingManifest = new DirectingManifest();
    Image image = new Image();
    TitleCard titleCard = new TitleCard();
    Footage footage = new Footage();
    Song song = new Song();

    // Provide a song.
    song.setSourceUrl("http://api.client.java.animoto.s3.amazonaws.com/test_assets/song.mp3");
    directingManifest.setSong(song);

    // Provide an image.
    image.setSourceUrl("http://api.client.java.animoto.s3.amazonaws.com/test_assets/image.jpg");
    directingManifest.addVisual(image);

    // Provide a title card.
    titleCard.setH1("hello");
    titleCard.setH2("world");
    directingManifest.addVisual(titleCard);

    // Provide a video.
    footage.setSourceUrl("http://api.client.java.animoto.s3.amazonaws.com/test_assets/footage.mp4");
    directingManifest.addVisual(footage);

    // Set the video title and producer name
    directingManifest.setTitle("My Animoto Video");
    directingManifest.setProducerName("Animoto");

    // Create a rendering manifest to control things like video resolution and 
    // framerate
    RenderingManifest renderingManifest = new RenderingManifest();
    RenderingProfile renderingProfile = new RenderingProfile();

    // Setup our rendering profile.
    renderingProfile.setFramerate(new Float(30));
    renderingProfile.setFormat(Format.H264);
    renderingProfile.setVerticalResolution(VerticalResolution.VR_720P);

    // Set the storyboard from the Directing Job into the Rendering Manifest.
    renderingManifest.setRenderingProfile(renderingProfile);
    renderingManifest.setStoryboard(directingJob.getStoryboard());

    // Send the job to the API.  Status updates will be communicated via a 
    // HTTP POST to "http://mysite.com/animoto_callback"
    httpCallbackUrl = "http://mysite.com/animoto_callback"
    httpCallbackFormat = "json"
    DirectingAndRenderingJob directingAndRenderingJob = null
    directingAndRenderingJob = apiClient.directAndRender(directingManifest, renderingManifest, httpCallbackUrl, httpCallbackFormat)

### Rendering an existing Storyboard

If you already have a storyboard (from a previous directing job), you can then
tell the API to render a video.

    RenderingJob renderingJob = null;
    RenderingManifest renderingManifest = new RenderingManifest();
    RenderingProfile renderingProfile = new RenderingProfile();

    // Setup our rendering profile.
    renderingProfile.setFramerate(new Float(30));
    renderingProfile.setFormat(Format.H264);
    renderingProfile.setVerticalResolution(VerticalResolution.VR_720P);

    // Set the storyboard from the Directing Job into the Rendering Manifest.
    renderingManifest.setRenderingProfile(renderingProfile);
    renderingManifest.setStoryboard(directingJob.getStoryboard());
    renderingJob = apiClient.render(renderingManifest);

    // Wait until it is completed or failed. (You could also use http 
    // callbacks...)
    while(renderingJob.isPending()) {

      // You should probably sleep here before calling reload.
      apiClient.reload(renderingJob);
    }

    if (renderingJob.isCompleted()) {
      // Now we have a video!
      // Remember to reload the Video if you want the metadata and link information.
      renderingJob.getVideo();
    }

### Working with Storyboards and Videos

When you have a Storyboard object, you must query the API to get all the information related to the resource.

    // We have Storyboard location/URL but no information :(
    storyboard.getLocation();

    apiClient.reload(storyboard);

    // Now we have critical Storyboard links and metadata! :)
    storyboard.getLinks();
    storyboard.getMetadata();

Similarly, when you have a Video object, you must query the API to get all the information related to the resource.

    // We have a Video location/URL but no information :(
    video.getLocation();

    apiClient.reload(video);

    // Now we have critical Storyboard links and metadata! :)
    video.getLinks();
    video.getMetadata();

### Command Line Interface (CLI)

A small CLI application is provided to help you run the API on the command
line. Assuming you have generated a JAR with all dependencies (and you just
don't like using curl on the command line), you can run the CLI with a utility
shell script that sets the classpath and executes the CLI class:

./cli.sh --help

### Integration Tests

By default when you run tests, only the unit tests are run. If you want to run
the integration test against the actual API services, then you can run the
primary integration test as follows:

  mvn -Dtest=ApiClientIntegrationTest test

You will need to have network connectivity for the integration test to work.
The integration test uses the ApiClientFactory which reads credentials from
the animoto_api_client.properties. Please edit this file under
src/test/resources, to add your own credentials.

### Wire Logging

You can use the built in features of Apache Http Client and Commons Logging to
view the network information between the Java API Client and the API. This
will allow you to use your current logging framework, whatever it is, to view
network information.

http://hc.apache.org/httpcomponents-client-4.0.1/logging.html

As a helper, you can enable wire logging by opening up the project object
model (POM) and enable the argList node for maven testing. The argList node
contains the recommended JVM arguments for wire logging. For the Animoto API
CLI, the cli wrapper script already contains wire logging arguments to the
JVM.

<a name="how_to_contribute"></a>
## How to contribute to this client

1. [Fork](http://help.github.com/forking/) `animoto_ruby_client`
2. Create a topic branch - `git checkout -b my_branch`
3. Push to your branch - `git push origin my_branch`
4. Create an [Issue](http://github.com/animoto/api_client_java/issues) with a link to your branch
5. That's it!

You might want to checkout our [the wiki page](http://wiki.github.com/animoto/api_client_java) for information
on coding standards, new features, etc.

[javadoc]: http://api.client.java.animoto.s3.amazonaws.com/apidocs/index.html
[api_docs]: http://animoto.com/developer/api
