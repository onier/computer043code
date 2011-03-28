///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package apaint;
//
///**
// * 浠ｇ悊Visio绫�
// *
// * @author Administrator
// *
// */
//class VisioAgent {
//
//    /**
//     * Visio瀵硅薄
//     */
//    private IDispatch visioapplication;
//    /**
//     * Visio Documents瀵硅薄
//     */
//    private IDispatch documents;
//    private IDispatch UMLDocument;
//    private IDispatch UMLVSSDocument;
//    private IDispatch UMLpages;
//    private IDispatch UMLpage;
//    private IDispatch UMLmasters;
//    public static final int ACTION_STATE = 0;
//    private IDispatch ASmaster;
//    public static final int INTERFACE = 1;
//    private IDispatch Interfacemaster;
//    public static final int FINAL_STATE = 2;
//    private IDispatch FSmaster;
//    public static final int INITIAL_STATE = 3;
//    private IDispatch ISmaster;
//    public static final int CONTROL_FLOW = 4;
//    private IDispatch CFmaster;
//    public static final int SWIMLANCE = 5;
//    private IDispatch SWmaster;
//    private IDispatch PageSheet;
//    /**
//     * vkd鍥鹃珮搴�
//     */
//    private double maxPix;
//    /**
//     * vdk鏈�皬x鍧愭爣
//     */
//    private double borderx;
//    private int resolution = Toolkit.getDefaultToolkit().getScreenResolution();
//    public static final int[] INTERFACE_CTRL_ID = new int[]{0, 5, 6, 9, 10};
//    public static final int[] StartEND_CTRL_ID = new int[]{0, 2, 4, 1, 3};
//
//    public VisioAgent(IDispatch app, boolean visible) throws JComException {
//        visioapplication = app;
//        documents = (IDispatch) visioapplication.get("Documents");
//    }
//
//    /**
//     * vkd鍥剧殑楂樺害
//     *
//     * @param pix
//     */
//    public void setMaxPix(int pix) {
//        this.maxPix = pix;
//    }
//
//    private IDispatch addShapeToVisio(int type) throws JComException {
//        IDispatch master = null;
//        switch (type) {
//            case 0:
//                master = ASmaster;
//                break;
//            case 1:
//                master = Interfacemaster;
//                break;
//            case 2:
//                master = FSmaster;
//                break;
//            case 3:
//                master = ISmaster;
//                break;
//            case 4:
//                master = CFmaster;
//                break;
//            case 5:
//                master = SWmaster;
//                break;
//        }
//        IDispatch shape = (IDispatch) UMLpage.method("Drop", new Object[]{
//                    master, 0, 0});
//        return shape;
//    }
//
//    /**
//     * 娣诲姞vkd IShape
//     *
//     * @param shapeList
//     * @throws JComException
//     */
//    @SuppressWarnings({"unchecked", "unchecked"})
//    public void addIShapes(List<IShape> shapeList) throws JComException {
//        this.borderx = this.getLeftBoder(shapeList);
//        this.resolution = shapeList.get(0).getResolution();
//        Map<String, IDispatch> shapeMap = new HashMap<String, IDispatch>();
//        List<LineShape> lineShapes = new ArrayList<LineShape>();
//        for (int i = 0; i < shapeList.size(); i++) {
//            if (shapeList.get(i) instanceof SwimlaneShape) {
//                this.addIShapeTOUMLDocument(shapeList.get(i), shapeMap);
//            }
//        }
//
//        for (int i = 0; i < shapeList.size(); i++) {
//            if (!(shapeList.get(i) instanceof SwimlaneShape)) {
//                if (shapeList.get(i) instanceof LineShape) {
//                    lineShapes.add((LineShape) shapeList.get(i));
//                } else {
//                    this.addIShapeTOUMLDocument(shapeList.get(i), shapeMap);
//                }
//            }
//        }
//        for (int i = 0; i < lineShapes.size(); i++) {
//            LineShape lineShape = lineShapes.get(i);
//            IDispatch shape = this.addShapeToVisio(CONTROL_FLOW);
//            initVisioInfo(shape, lineShape, shapeMap);
//            IDispatch cell = (IDispatch) shape.get("Cells",
//                    new Object[]{"LockTextEdit"});
//            cell.put("Formula", false);
//            shape.put("Text", lineShape.getText());
//            cell = (IDispatch) shape.get("Cells",
//                    new Object[]{"ShapeRouteStyle"});
//            cell.put("Formula", 2);
//            Object[] objs;
//            String startShapeName = null, endShapeName = null;
//            int startIndex = 0, endIndex = 0;
//            Object obj = lineShape.getConnectionRelationObject();
//            if (obj.getClass().isArray()) {
//                objs = (Object[]) obj;
//            } else {
//                return;
//            }
//            if (objs == null || objs.length != 2) {
//                System.out.println("璇ユ暟鎹笉瀹屾暣锛�);
//
//                return;
//            } else {
//                ConnectionRelation connection;
//                IShape start = (IShape) objs[0];
//                IShape end = (IShape) objs[1];
//                List list;
//                if (start != null) {
//                    obj = start.getConnectionRelationObject();
//                    if (obj instanceof List) {
//                        list = (List) obj;
//                        for (int j = 0; j < list.size(); j++) {
//                            if (list.get(j) instanceof ConnectionRelation) {
//                                connection = (ConnectionRelation) list.get(j);
//                                if (connection.getShape().getShape_id() == lineShape.getShape_id()) {
//
//                                    if (connection.getLinePointId() == 0) {
//                                        startShapeName = shapeMap.get(
//                                                new Long(start.getShape_id()).toString()).get(
//                                                "NameU").toString();
//                                        startIndex = connection.getCtrlId();
//                                        if (start instanceof InterfaceShape) {
//                                            startIndex = INTERFACE_CTRL_ID[startIndex];
//                                        }
//                                        if (start instanceof BeginShape
//                                                || start instanceof EndShape) {
//                                            startIndex = StartEND_CTRL_ID[startIndex];
//                                        }
//                                    } else {
//                                        endShapeName = shapeMap.get(
//                                                new Long(start.getShape_id()).toString()).get(
//                                                "NameU").toString();
//                                        endIndex = connection.getCtrlId();
//                                        if (start instanceof InterfaceShape) {
//                                            endIndex = INTERFACE_CTRL_ID[endIndex];
//                                        }
//                                        if (start instanceof BeginShape
//                                                || start instanceof EndShape) {
//                                            endIndex = StartEND_CTRL_ID[endIndex];
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                } else {
//                    cell = (IDispatch) shape.get("Cells",
//                            new Object[]{"BeginX"});
//                    cell.put("Formula", getNewValue(lineShape.getMline().x1
//                            - borderx)
//                            + "mm");
//                    cell = (IDispatch) shape.get("Cells",
//                            new Object[]{"BeginY"});
//                    cell.put("Formula", getNewValue(maxPix
//                            - lineShape.getMline().y1)
//                            + "mm");
//                }
//                if (end != null) {
//                    obj = end.getConnectionRelationObject();
//                    if (obj instanceof List) {
//                        list = (List) obj;
//                        for (int j = 0; j < list.size(); j++) {
//                            if (list.get(j) instanceof ConnectionRelation) {
//                                connection = (ConnectionRelation) list.get(j);
//                                if (connection.getShape().getShape_id() == lineShape.getShape_id()) {
//
//                                    if (connection.getLinePointId() == 0) {
//                                        startShapeName = shapeMap.get(
//                                                new Long(start.getShape_id()).toString()).get(
//                                                "NameU").toString();
//                                        startIndex = connection.getCtrlId();
//                                        if (end instanceof InterfaceShape) {
//                                            startIndex = INTERFACE_CTRL_ID[startIndex];
//                                        }
//                                        if (end instanceof BeginShape
//                                                || end instanceof EndShape) {
//                                            startIndex = StartEND_CTRL_ID[startIndex];
//                                        }
//                                    } else {
//                                        endShapeName = shapeMap.get(
//                                                new Long(end.getShape_id()).toString()).get(
//                                                "NameU").toString();
//                                        endIndex = connection.getCtrlId();
//                                        if (end instanceof InterfaceShape) {
//                                            endIndex = INTERFACE_CTRL_ID[endIndex];
//                                        }
//                                        if (end instanceof BeginShape
//                                                || end instanceof EndShape) {
//                                            endIndex = StartEND_CTRL_ID[endIndex];
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                } else {
//                    cell = (IDispatch) shape.get("Cells",
//                            new Object[]{"EndX"});
//                    cell.put("Formula", getNewValue(lineShape.getMline().x2
//                            - borderx)
//                            + "mm");
//                    cell = (IDispatch) shape.get("Cells",
//                            new Object[]{"EndY"});
//                    cell.put("Formula", getNewValue(maxPix
//                            - lineShape.getMline().y2)
//                            + "mm");
//                }
//            }
//            if (startShapeName != null) {
//                cell = (IDispatch) shape.get("Cells", new Object[]{"BeginX"});
//                cell.put("Formula", getControlFlowFormula(startShapeName,
//                        startIndex));
//                cell = (IDispatch) shape.get("Cells", new Object[]{"BeginY"});
//                cell.put("Formula", getControlFlowFormula(startShapeName,
//                        startIndex));
//            }
//            if (endShapeName != null) {
//                cell = (IDispatch) shape.get("Cells", new Object[]{"EndX"});
//                cell.put("Formula", getControlFlowFormula(endShapeName,
//                        endIndex));
//                cell = (IDispatch) shape.get("Cells", new Object[]{"EndY"});
//                cell.put("Formula", getControlFlowFormula(endShapeName,
//                        endIndex));
//            }
//        }
//        // this.UMLpage.method("Layout", new Object[]{});
//        // this.UMLpage.method("ResizeToFitContents", new Object[] {});
//    }
//
//    private void addIShapeTOUMLDocument(IShape ishape,
//            Map<String, IDispatch> map) throws JComException {
//
//        if (ishape instanceof BeginShape) {
//            BeginShape bshape = (BeginShape) ishape;
//            java.awt.geom.Ellipse2D.Double ellipse = bshape.getEllipse();
//            double x = ellipse.x + ellipse.width / 2;
//            double y = ellipse.y + ellipse.height / 2;
//            y = maxPix - y;
//            x = x - borderx;
//            x = this.getNewValue(x);
//            y = this.getNewValue(y);
//            IDispatch initState = this.addShapeToVisio(INITIAL_STATE);
//            initVisioInfo(initState, bshape, map);
//            IDispatch cell = (IDispatch) initState.get("Cells",
//                    new Object[]{"LockTextEdit"});
//            cell.put("Formula", false);
//            initState.put("Text", bshape.getText());
//            cell = (IDispatch) initState.get("Cells", new Object[]{"PinX"});
//            cell.put("Formula", x + "mm");
//            cell = (IDispatch) initState.get("Cells", new Object[]{"PinY"});
//            cell.put("Formula", y + "mm");
//        } else if (ishape instanceof InterfaceShape) {
//            InterfaceShape interfaceShape = (InterfaceShape) ishape;
//            IDispatch interShape = this.addShapeToVisio(INTERFACE);
//            java.awt.geom.Rectangle2D.Double rect = interfaceShape.getRectangle();
//            initVisioInfo(interShape, interfaceShape, map);
//            double x = rect.x + rect.width / 2;
//            double y = rect.y + rect.height / 2;
//            y = maxPix - y;
//            x = x - borderx;
//            x = this.getNewValue(x);
//            y = this.getNewValue(y);
//            double w = getNewValue(rect.width);
//            // double h = getNewValue(rect.height);
//            interShape.put("Text", interfaceShape.getText());
//            IDispatch cell = (IDispatch) interShape.get("Cells",
//                    new Object[]{"PinX"});
//            cell.put("Formula", x + "mm");
//            cell = (IDispatch) interShape.get("Cells", new Object[]{"PinY"});
//            cell.put("Formula", y + "mm");
//            cell = (IDispatch) interShape.get("Cells",
//                    new Object[]{"LockWidth"});
//            cell.put("Formula", false);
//            cell = (IDispatch) interShape.get("Cells",
//                    new Object[]{"LockHeight"});
//            cell.put("Formula", false);
//            cell = (IDispatch) interShape.get("Cells", new Object[]{"Width"});
//            cell.put("Formula", w + "mm");
//            // cell = (IDispatch) interShape.get("Cells",
//            // new Object[] { "Height" });
//            // cell.put("Formula", h + "mm");
//        } else if (ishape instanceof SwimlaneShape) {
//            SwimlaneShape swShape = (SwimlaneShape) ishape;
//            IDispatch swimlaneShape = this.addShapeToVisio(SWIMLANCE);
//            java.awt.geom.Rectangle2D.Double rect = swShape.getRectangle();
//            initVisioInfo(swimlaneShape, swShape, map);
//            double x = rect.x + rect.width / 2;
//            double y = maxPix - rect.height / 2;
//            double w = rect.width;
//            double h = rect.height;
//            x = x - borderx;
//            x = this.getNewValue(x);
//            y = this.getNewValue(y);
//            w = this.getNewValue(w);
//            h = this.getNewValue(h);
//            IDispatch cell = (IDispatch) swimlaneShape.get("Cells",
//                    new Object[]{"LockTextEdit"});
//            cell.put("Formula", false);
//            swimlaneShape.put("Text", swShape.getText());
//            cell = (IDispatch) swimlaneShape.get("Cells",
//                    new Object[]{"PinX"});
//            cell.put("Formula", x + "mm");
//            cell = (IDispatch) swimlaneShape.get("Cells",
//                    new Object[]{"PinY"});
//            cell.put("Formula", y + "mm");
//            cell = (IDispatch) swimlaneShape.get("Cells",
//                    new Object[]{"Width"});
//            cell.put("Formula", w + "mm");
//            cell = (IDispatch) swimlaneShape.get("Cells",
//                    new Object[]{"Height"});
//            cell.put("Formula", h + "mm");
//        } else if (ishape instanceof EndShape) {
//            EndShape eShape = (EndShape) ishape;
//            IDispatch endShape = this.addShapeToVisio(FINAL_STATE);
//            initVisioInfo(endShape, eShape, map);
//            java.awt.geom.Ellipse2D.Double ellipse = eShape.getEllipse();
//            double x = ellipse.x + ellipse.width / 2;
//            double y = ellipse.y + ellipse.height / 2;
//            y = maxPix - y;
//            x = x - borderx;
//            x = this.getNewValue(x);
//            y = this.getNewValue(y);
//            IDispatch cell = (IDispatch) endShape.get("Cells",
//                    new Object[]{"LockTextEdit"});
//            cell.put("Formula", false);
//            endShape.put("Text", eShape.getText());
//            cell = (IDispatch) endShape.get("Cells", new Object[]{"PinX"});
//            cell.put("Formula", x + "mm");
//            cell = (IDispatch) endShape.get("Cells", new Object[]{"PinY"});
//            cell.put("Formula", y + "mm");
//        } else if (ishape instanceof StateShape) {
//            StateShape stateShape = (StateShape) ishape;
//            java.awt.geom.Ellipse2D.Double ellipse = stateShape.getEllipse();
//            double x = ellipse.x + ellipse.width / 2;
//            double y = ellipse.y + ellipse.height / 2;
//            y = maxPix - y;
//            x = x - borderx;
//            x = this.getNewValue(x);
//            y = this.getNewValue(y);
//            double w = getNewValue(ellipse.width);
//            // double h = getNewValue(ellipse.height);
//            IDispatch shape = this.addShapeToVisio(ACTION_STATE);
//            shape.put("Text", stateShape.getText());
//            initVisioInfo(shape, stateShape, map);
//            IDispatch cell = (IDispatch) shape.get("Cells",
//                    new Object[]{"LockWidth"});
//            cell.put("Formula", false);
//            cell = (IDispatch) shape.get("Cells", new Object[]{"LockHeight"});
//            cell.put("Formula", false);
//            cell = (IDispatch) shape.get("Cells", new Object[]{"PinX"});
//            cell.put("Formula", x + "mm");
//            cell = (IDispatch) shape.get("Cells", new Object[]{"PinY"});
//            cell.put("Formula", y + "mm");
//            cell = (IDispatch) shape.get("Cells", new Object[]{"Width"});
//            cell.put("Formula", w + "mm");
//
//            // cell = (IDispatch) shape.get("Cells", new Object[] { "Height" });
//            // cell.put("Formula", h + "mm");
//        }
//
//    }
//
//    /**
//     * 鑾峰緱vkd鍥炬渶宸﹁竟鐨剎鍧愭爣
//     *
//     * @param ishapeList
//     * @return
//     */
//    public double getLeftBoder(List<IShape> ishapeList) {
//        IShape ishape;
//        double x = 9999999;
//        for (int i = 0; i < ishapeList.size(); i++) {
//            ishape = ishapeList.get(i);
//            if (ishape instanceof AbstractEllipseShape) {
//                AbstractEllipseShape bshape = (AbstractEllipseShape) ishape;
//                java.awt.geom.Ellipse2D.Double rect = bshape.getEllipse();
//                x = Math.min(rect.x, x);
//            } else if (ishape instanceof AbstractRectangleShape) {
//                AbstractRectangleShape shpe = (AbstractRectangleShape) ishape;
//                java.awt.geom.Rectangle2D.Double rect = shpe.getRectangle();
//                x = Math.min(rect.x, x);
//            } else if (ishape instanceof LineShape) {
//                LineShape shape = (LineShape) ishape;
//                x = Math.min(shape.getMline().x1, x);
//                x = Math.min(shape.getMline().x2, x);
//            }
//        }
//        return x;
//    }
//
//    /**
//     * vkd鍥炬渶澶ч珮搴�
//     *
//     * @param ishapeList
//     * @return
//     */
//    public Dimension getMaxSize(List<IShape> ishapeList) {
//        IShape ishape;
//        double maxH = 0, maxW = 0;
//        for (int i = 0; i < ishapeList.size(); i++) {
//            ishape = ishapeList.get(i);
//            if (ishape instanceof AbstractEllipseShape) {
//                AbstractEllipseShape bshape = (AbstractEllipseShape) ishape;
//                java.awt.geom.Ellipse2D.Double rect = bshape.getEllipse();
//                if (!(ishape instanceof SwimlaneShape)) {
//                    maxH = Math.max(rect.getMaxY(), maxH);
//                }
//                maxW = Math.max(rect.getMaxX(), maxW);
//            } else if (ishape instanceof AbstractRectangleShape) {
//                AbstractRectangleShape shpe = (AbstractRectangleShape) ishape;
//                java.awt.geom.Rectangle2D.Double rect = shpe.getRectangle();
//                if (!(ishape instanceof SwimlaneShape)) {
//                    maxH = Math.max(rect.getMaxY(), maxH);
//                }
//                maxW = Math.max(rect.getMaxX(), maxW);
//            } else if (ishape instanceof LineShape) {
//                LineShape shape = (LineShape) ishape;
//                maxH = Math.max(shape.getMline().y1, maxH);
//                maxH = Math.max(shape.getMline().y2, maxH);
//                maxW = Math.max(shape.getMline().x1, maxW);
//                maxW = Math.max(shape.getMline().x2, maxW);
//            }
//        }
//        return new Dimension((int) maxW + 100, (int) maxH + 100);
//    }
//
//    /**
//     * 鑾峰緱Visio涓洿绾跨殑鍏宠仈鐐�
//     *
//     * @param shapeName
//     * @param ctrlIndex
//     * @return
//     */
//    private String getControlFlowFormula(String shapeName, int ctrlIndex) {
//        return "=PAR(PNT(" + shapeName + "!Connections.X" + ctrlIndex + ","
//                + shapeName + "!Connections.Y" + ctrlIndex + "))";
//    }
//
//    /**
//     * 鑾峰緱visio鏂囦欢鐨勪妇渚�鍗曚綅涓簃m
//     *
//     * @param pixIndex
//     *            vkd鏂囦欢涓湪褰撳墠鍒嗚鲸鐜囦笅鐐瑰潗鏍�
//     * @return
//     */
//    private double getNewValue(double pixIndex) {
//        double value = 0;
//        double length = pixIndex / resolution;
//        value = length * 25.4;
//        return value;
//    }
//
//    private void initVisioInfo(IDispatch shape, IShape iShape,
//            Map<String, IDispatch> map) throws JComException {
//
//        shape.put("Data1", "璀﹀憡 锛氳鍕夸慨鏀逛笅鏂圭殑鏁版嵁");
//        shape.put("Data2", new Long(iShape.getShape_id()).toString());
//        shape.put("Data3", "璀﹀憡 锛氳鍕夸慨鏀逛笂鏂圭殑鏁版嵁");
//        map.put(new Long(iShape.getShape_id()).toString(), shape);
//
//    }
//
//    /**
//     * mm
//     *
//     * @param width
//     * @param height
//     * @throws JComException
//     */
//    public void setPageSize(double width, double height) throws JComException {
//        IDispatch cell = (IDispatch) PageSheet.method("Cells",
//                new Object[]{"PageWidth"});
//        cell.put("Formula", width + "mm");
//        cell = (IDispatch) PageSheet.method("Cells",
//                new Object[]{"PageHeight"});
//        cell.put("Formula", height + "mm");
//    }
//
//    public void addUMLDocument() throws JComException {
//        File file = new File("model\\TCD_VS_MODEL.VSD");
//        System.out.println(file.getAbsolutePath());
//        UMLDocument = (IDispatch) documents.method("add", new String[]{file.getAbsolutePath()});
//        UMLpages = (IDispatch) UMLDocument.get("Pages");
//        UMLpage = (IDispatch) UMLpages.method("item", new Object[]{1});
//        PageSheet = (IDispatch) UMLpage.get("PageSheet");
//
//    }
//
//    public void save(String file) throws JComException {
//        UMLDocument.method("SaveAs", new Object[]{file});
//    }
//
//    public void loadUMLVSSLibrary() throws JComException {
//        File file = new File("model\\TCD_VS_MODEL.vss");
//        UMLVSSDocument = (IDispatch) documents.method("OpenEx", new Object[]{
//                    file.getAbsolutePath(), "&H4"});
//        UMLmasters = (IDispatch) UMLVSSDocument.get("Masters");
//        CFmaster = (IDispatch) UMLmasters.get("ItemU",
//                new String[]{"Control Flow"});
//        ISmaster = (IDispatch) UMLmasters.get("ItemU",
//                new String[]{"Initial State"});
//        FSmaster = (IDispatch) UMLmasters.get("ItemU",
//                new String[]{"Final State"});
//        SWmaster = (IDispatch) UMLmasters.get("ItemU",
//                new String[]{"Swimlane"});
//        ASmaster = (IDispatch) UMLmasters.get("ItemU", new String[]{"TCD鐘舵�"});
//        Interfacemaster = (IDispatch) UMLmasters.get("ItemU",
//                new String[]{"Interface "});
//
//    }
//
//    public IDispatch getDocuments() {
//        return documents;
//    }
//
//    public void setDocuments(IDispatch documents) {
//        this.documents = documents;
//    }
//
//    public IDispatch getVisioapplication() {
//        return visioapplication;
//    }
//}

