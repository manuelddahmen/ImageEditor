package one.empty3.feature_arr_style;/*
XmlTrace

for (Node node : nodes) {
    Element element = (Element)node;
    Iterator<Element> iterator = element.elementIterator("title");
    while (iterator.hasNext()) {
        Element title =(Element)iterator.next();
        title.setText(title.getText() + " updated");
    }
}
XMLWriter writer = new XMLWriter(
  new FileWriter(new File("src/test/resources/example_updated.xml")));
writer.write(document);
writer.close();
*/
