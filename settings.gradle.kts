plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "SoftKompProject"
include("specifikacija")
include("kalkulacije")
include("TXTImplementation")
include("testApp")
include("CSVImplementation")
include("EXCELImplementation")
include("PDFImplementation")

