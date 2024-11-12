# Editor2D

## Description
Editor2D is a simple 2D image editor designed to work with PNM format images, supporting `.pbm`, `.pgm`, and `.ppm` files. The application allows users to open images, apply a series of filters, and export the results in various formats. It is an ideal project for anyone looking to modify images quickly and intuitively.

## Features

- **Open Images**: Allows users to open images in supported PNM formats.
  
- **Filter Pipeline**: Users can set up a pipeline of filters to apply to the image. Available filters include:
  - Flip horizontally (side by side)
  - Flip vertically (up down)
  - Rotate clockwise 90°
  - Rotate counterclockwise 90°
  - Negative colors

- **Execute Pipeline**: Once created, the filter pipeline can be executed to apply changes to the image. Users can also clear the pipeline at any time.

- **Image Export**: Images can be exported in `.pbm`, `.pgm`, and `.ppm` formats, regardless of the currently opened image format. The application automatically handles necessary conversions.

- **Language Customization**: Users can choose between English and Italian for the user interface.

- **Undo and Redo Functionality (under development)**: The editor supports undo and redo, allowing users to easily revert to the previous or next image state (if an undo operation has been performed).

- **Zoom In and Zoom Out**: Users can perform progressive zoom in and out on the image for detailed analysis.

- **Help and Information**: The app includes a "Help -> About" section that displays developer information and additional details about the application.

## Requirements

- JDK 21 or higher
- A compatible operating system (Windows, macOS, Linux)

## Instructions for Use

1. **Launch the Application**: Run the `Editor2D.jar` file to start the editor.
2. **Open an Image**: Select "File" -> "Open" and choose a PNM file from your system.
3. **Apply Filters**: Select the desired filters and set up the pipeline.
4. **Execute the Pipeline**: Click on "Edit" -> "Run pipeline" to apply the filters to the image or on the ">" icon.
5. **Export the Image**: Select "File" -> "Export" to save the image in the desired format.
6. **Change Language**: Go to "Edit" -> "Change language" to choose your preferred language (Italian or English).
7. **Use Undo and Redo**: Use the options in the "Edit" menu to undo or redo changes or click on the relative arrow icons.
8. **Zoom**: Use the zoom controls under "Edit" to enlarge or reduce the image view.

## Clone

```sh
git clone https://github.com/DominikPanzarella/editor2d.git
```

## Install

Install the backend library

```sh
cd backed && mvn clean install
```

Create the jar file

```sh
cd frontend && mvn clean package
```

## Usage

```sh
cd frontend && java -jar target/2dEditor-jar-with-dependencies.jar
```

## Contribute

For contributions, bug reports, or suggestions, feel free to open an issue on GitHub.

## Authors

👤 **Dominik Panzarella**
[![Instagram](https://img.shields.io/badge/Instagram-%23E4405F.svg?logo=Instagram&logoColor=white)](https://www.instagram.com/__dom_/) [![LinkedIn](https://img.shields.io/badge/LinkedIn-%230077B5.svg?logo=linkedin&logoColor=white)](https://www.linkedin.com/in/dominik-panzarella-a8412817a) [![Stack Overflow](https://img.shields.io/badge/-Stackoverflow-FE7A16?logo=stack-overflow&logoColor=white)](https://stackoverflow.com/users/21978407/dominik-panzarella) [![YouTube](https://img.shields.io/badge/YouTube-%23FF0000.svg?logo=YouTube&logoColor=white)](https://www.youtube.com/channel/UC128UoG-qfNOf6TCjarx5Mw) 

## Contact

For more information, you can contact the developers via email.

---

## License
This project is part of a school assignment for the SUPSI (Scuola Universitaria Professionale della Svizzera italiana). It is not intended for commercial use.
