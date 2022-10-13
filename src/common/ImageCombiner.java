package common;

import world.AgentBlock;
import world.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageCombiner {

    private final BufferedImage x ;
    private final BufferedImage v ;
    private final BufferedImage vg ;
    private final BufferedImage vb ;
    private final BufferedImage vs ;
    private final BufferedImage vgb ;
    private final BufferedImage vgs ;
    private final BufferedImage vgbs ;
    private final BufferedImage av ;
    private final BufferedImage avg ;
    private final BufferedImage avb ;
    private final BufferedImage avs ;
    private final BufferedImage avgb ;
    private final BufferedImage avgs ;
    private final BufferedImage avgbs ;
    private final BufferedImage b ;
    private final BufferedImage s ;
    private final BufferedImage bs ;
    private final BufferedImage p ;
    private final BufferedImage w ;

    private static ImageCombiner instance=null;

    public static BufferedImage getCombinedBoardState() {
        if(instance==null)
            instance = new ImageCombiner();
        return instance.combiner();

    }
    private ImageCombiner() {

        try {
            x = ImageIO.read(new File("./arts/allPossibleCombination/0.x.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            v = ImageIO.read(new File("./arts/allPossibleCombination/1.v.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            vg = ImageIO.read(new File("./arts/allPossibleCombination/2.vg.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            vb = ImageIO.read(new File("./arts/allPossibleCombination/3.vb.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            vs = ImageIO.read(new File("./arts/allPossibleCombination/4.vs.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            vgb = ImageIO.read(new File("./arts/allPossibleCombination/5.vgb.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            vgs = ImageIO.read(new File("./arts/allPossibleCombination/6.vgs.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            vgbs = ImageIO.read(new File("./arts/allPossibleCombination/7.vgbs.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            av = ImageIO.read(new File("./arts/allPossibleCombination/8.av.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            avg = ImageIO.read(new File("./arts/allPossibleCombination/9.avg.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            avb = ImageIO.read(new File("./arts/allPossibleCombination/10.avb.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            avs = ImageIO.read(new File("./arts/allPossibleCombination/11.avs.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            avgb = ImageIO.read(new File("./arts/allPossibleCombination/12.avgb.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            avgs = ImageIO.read(new File("./arts/allPossibleCombination/13.avgs.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            avgbs = ImageIO.read(new File("./arts/allPossibleCombination/14.avgbs.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            b = ImageIO.read(new File("./arts/allPossibleCombination/15.b.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            s = ImageIO.read(new File("./arts/allPossibleCombination/16.s.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            bs = ImageIO.read(new File("./arts/allPossibleCombination/17.bs.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            p = ImageIO.read(new File("./arts/allPossibleCombination/18.p.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            w = ImageIO.read(new File("./arts/allPossibleCombination/19.w.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    private BufferedImage combiner(){
        AgentBlock[][] blocks = Board.getInstance().getBlocks();

        BufferedImage retImage = new BufferedImage(650,650,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = retImage.createGraphics();

        for (int i=0;i<10;i++){
            for(int j=0; j<10;j++){
                String state = blocks[i][j].stateForImage();
                switch (state) {
                    case "x" -> g2d.drawImage(x, j * 65, i * 65, 65, 65, null);
                    case "v" -> g2d.drawImage(v, j * 65, i * 65, 65, 65, null);
                    case "vg" -> g2d.drawImage(vg, j * 65, i * 65, 65, 65, null);
                    case "vb" -> g2d.drawImage(vb, j * 65, i * 65, 65, 65, null);
                    case "vs" -> g2d.drawImage(vs, j * 65, i * 65, 65, 65, null);
                    case "vgb" -> g2d.drawImage(vgb, j * 65, i * 65, 65, 65, null);
                    case "vgs" -> g2d.drawImage(vgs, j * 65, i * 65, 65, 65, null);
                    case "vgbs" -> g2d.drawImage(vgbs, j * 65, i * 65, 65, 65, null);
                    case "av" -> g2d.drawImage(av, j * 65, i * 65, 65, 65, null);
                    case "avg" -> g2d.drawImage(avg, j * 65, i * 65, 65, 65, null);
                    case "avb" -> g2d.drawImage(avb, j * 65, i * 65, 65, 65, null);
                    case "avs" -> g2d.drawImage(avs, j * 65, i * 65, 65, 65, null);
                    case "avgb" -> g2d.drawImage(avgb, j * 65, i * 65, 65, 65, null);
                    case "avgs" -> g2d.drawImage(avgs, j * 65, i * 65, 65, 65, null);
                    case "avgbs" -> g2d.drawImage(avgbs, j * 65, i * 65, 65, 65, null);
                    case "b" -> g2d.drawImage(b, j * 65, i * 65, 65, 65, null);
                    case "s" -> g2d.drawImage(s, j * 65, i * 65, 65, 65, null);
                    case "bs" -> g2d.drawImage(bs, j * 65, i * 65, 65, 65, null);
                    case "p" -> g2d.drawImage(p, j * 65, i * 65, 65, 65, null);
                    case "w" -> g2d.drawImage(w, j * 65, i * 65, 65, 65, null);
                }
            }
        }
        g2d.dispose();
        return retImage;
    }

    private BufferedImage combinePixelByPixel(){
        AgentBlock[][] blocks = Board.getInstance().getBlocks();
        int [][] pixels = new int[650][650];

        for (int i=0;i<10;i++){
            for(int j=0; j<10;j++){
                String state = blocks[i][j].stateForImage();
                BufferedImage blockImage = switch (state) {
                    case "x" -> x;
                    case "v" -> v;
                    case "vg" -> vg;
                    case "vb" -> vb;
                    case "vs" -> vs;
                    case "vgb" -> vgb;
                    case "vgs" -> vgs;
                    case "vgbs" -> vgbs;
                    case "av" -> av;
                    case "avg" -> avg;
                    case "avb" -> avb;
                    case "avs" -> avs;
                    case "avgb" -> avgb;
                    case "avgs" -> avgs;
                    case "avgbs" -> avgbs;
                    case "b" -> b;
                    case "s" -> s;
                    case "bs" -> bs;
                    case "p" -> p;
                    case "w" -> w;
                    default -> x;
                };

                for (int ini =0; ini <65; ini++){
                    for(int inj=0;inj <65; inj ++ ){
                        int c = blockImage.getRGB(ini,inj);
                        pixels[i*65+ini][j*65+inj] = c;
                    }
                }
            }
        }

        BufferedImage retImg = new BufferedImage(650,650,BufferedImage.TYPE_INT_ARGB);
        for (int i=0;i<650;i++){
            for(int j=0;j<650;j++){
                retImg.setRGB(i,j,pixels[i][j]);
            }
        }
        return retImg;
    }
}
