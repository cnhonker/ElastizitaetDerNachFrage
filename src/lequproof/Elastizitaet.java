package lequproof;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.panel.*;
import org.jfree.chart.plot.*;
import org.jfree.data.xy.*;

/**
 * 
 * @author ry
 */
public class Elastizitaet extends JFrame{
    
    public Elastizitaet() throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800,600));
        getContentPane().add(getChartPanel(), BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }
    
    private JPanel getChartPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        final ChartPanel chartP = new ChartPanel(createChart());
        final CrosshairOverlay layer = createOverlay();
        chartP.addOverlay(layer);
        chartP.addChartMouseListener(new ChartMouseListener() {

            @Override
            public void chartMouseClicked(ChartMouseEvent cme) {
                //
            }

            @Override
            public void chartMouseMoved(ChartMouseEvent cme) {
                Rectangle2D dataArea = chartP.getScreenDataArea();
                JFreeChart chart = cme.getChart();
                XYPlot plot = chart.getXYPlot();
                ValueAxis xAxis = plot.getDomainAxis();
                ValueAxis yAxis = plot.getRangeAxis();
                double x = xAxis.java2DToValue(cme.getTrigger().getX(), dataArea, plot.getDomainAxisEdge());
                double y = yAxis.java2DToValue(cme.getTrigger().getY(), dataArea, plot.getRangeAxisEdge());
                ((Crosshair)layer.getDomainCrosshairs().get(0)).setValue(x);
                ((Crosshair)layer.getRangeCrosshairs().get(0)).setValue(y);
            }
        });
        panel.add(chartP,BorderLayout.CENTER);
        return panel;
    }
    
    private XYDataset createDataSet() {
        XYSeriesCollection dataSet = new XYSeriesCollection();
        XYSeries points = new XYSeries("Verlauf");
        points.add(0, 500);
        points.add(100, 0);
        dataSet.addSeries(points);
        return dataSet;
    }
    
    private JFreeChart createChart() {
        String title = "Elastizität der Nachfrage";
        String xLabel = "Menge (Stck.)";
        String yLabel = "Preis (EUR)";
        JFreeChart chart = ChartFactory.createXYLineChart(title, xLabel, yLabel, createDataSet(), PlotOrientation.VERTICAL, true, true, false);
        NumberAxis xAxis = new NumberAxis();
        xAxis.setTickUnit(new NumberTickUnit(10));
        NumberAxis yAxis = new NumberAxis();
        chart.getXYPlot().setDomainAxis(xAxis);
        chart.getXYPlot().setRangeAxis(yAxis);
        return chart;
    }
    
    private CrosshairOverlay createOverlay() {
        CrosshairOverlay overlay = new CrosshairOverlay();
        Crosshair xCrosshair = new Crosshair(Double.NaN, Color.RED, new BasicStroke(2f));
        Crosshair yCrosshair = new Crosshair(Double.NaN, Color.RED, new BasicStroke(2f));
        xCrosshair.setLabelVisible(true);
        yCrosshair.setLabelVisible(true);
        overlay.addDomainCrosshair(xCrosshair);
        overlay.addRangeCrosshair(yCrosshair);
        return overlay;
    }
    
    private static void createAndShowGui() {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new Elastizitaet().setVisible(true);
            }
        });
    }
    
    public static void main(String[] args) {
        createAndShowGui();
    }
}
