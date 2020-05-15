package shipinjianqie;

/**
 * Created by Administrator on 2018/7/23.
 */

import android.os.Environment;
import android.util.Log;

import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.FileDataSourceImpl;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.AACTrackImpl;
import com.googlecode.mp4parser.authoring.tracks.AppendTrack;
import com.googlecode.mp4parser.authoring.tracks.CroppedTrack;
import com.googlecode.mp4parser.authoring.tracks.TextTrackImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static shipinjianqie.VideoHelper.correctTimeToSyncSample;

/**
 * mp4处理公共类
 * Created by lxy on 17-4-21.
 */

public class Mp4ParseUtil {


    /**
     * 对Mp4文件集合进行追加合并(按照顺序一个一个拼接起来)
     *
     * @param mp4PathList [输入]Mp4文件路径的集合(支持m4a)(不支持wav)
     * @param outPutPath  [输出]结果文件全部名称包含后缀(比如.mp4)
     * @throws IOException 格式不支持等情况抛出异常
     */
    public static void appendMp4List(List<String> mp4PathList, String outPutPath){


        try {

            List<Movie> mp4MovieList = new ArrayList<>();// Movie对象集合[输入]
            for (String mp4Path : mp4PathList) {// 将每个文件路径都构建成一个Movie对象
                mp4MovieList.add(MovieCreator.build(mp4Path));
            }

            List<Track> audioTracks = new LinkedList<>();// 音频通道集合
            List<Track> videoTracks = new LinkedList<>();// 视频通道集合

            for (Movie mp4Movie : mp4MovieList) {// 对Movie对象集合进行循环
                for (Track inMovieTrack : mp4Movie.getTracks()) {
                    if ("soun".equals(inMovieTrack.getHandler())) {// 从Movie对象中取出音频通道
                        audioTracks.add(inMovieTrack);
                    }
                    if ("vide".equals(inMovieTrack.getHandler())) {// 从Movie对象中取出视频通道
                        videoTracks.add(inMovieTrack);
                    }
                }
            }
            Movie resultMovie = new Movie();// 结果Movie对象[输出]
            if (!audioTracks.isEmpty()) {// 将所有音频通道追加合并
                resultMovie.addTrack(new AppendTrack(audioTracks.toArray(new Track[audioTracks.size()])));
            }

            if (!videoTracks.isEmpty()) {// 将所有视频通道追加合并
                resultMovie.addTrack(new AppendTrack(videoTracks.toArray(new Track[videoTracks.size()])));
            }

            Container outContainer = new DefaultMp4Builder().build(resultMovie);// 将结果Movie对象封装进容器
            FileChannel fileChannel = new RandomAccessFile(String.format(outPutPath), "rw").getChannel();
            outContainer.writeContainer(fileChannel);// 将容器内容写入磁盘
            fileChannel.close();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 对AAC文件集合进行追加合并(按照顺序一个一个拼接起来)
     *
     * @param aacPathList [输入]AAC文件路径的集合(不支持wav)
     * @param outPutPath  [输出]结果文件全部名称包含后缀(比如.aac)
     * @throws IOException 格式不支持等情况抛出异常
     */
    public static void appendAacList(List<String> aacPathList, String outPutPath){

        try{

            List<Track> audioTracks = new LinkedList<>();// 音频通道集合
            for (int i = 0; i < aacPathList.size(); i++) {// 将每个文件路径都构建成一个AACTrackImpl对象
                audioTracks.add(new AACTrackImpl(new FileDataSourceImpl(aacPathList.get(i))));
            }

            Movie resultMovie = new Movie();// 结果Movie对象[输出]
            if (!audioTracks.isEmpty()) {// 将所有音频通道追加合并
                resultMovie.addTrack(new AppendTrack(audioTracks.toArray(new Track[audioTracks.size()])));
            }

            Container outContainer = new DefaultMp4Builder().build(resultMovie);// 将结果Movie对象封装进容器
            FileChannel fileChannel = new RandomAccessFile(String.format(outPutPath), "rw").getChannel();
            outContainer.writeContainer(fileChannel);// 将容器内容写入磁盘
            fileChannel.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private static List<Movie> moviesList = new ArrayList<>();
    private static List<Track> videoTracks = new ArrayList<>();
    private static List<Track> audioTracks = new ArrayList<>();
    //将两个mp4视频进行拼接
    public static void appendMp4(List<String> mMp4List,String outputpath){


        try {
            for (int i=0;i<mMp4List.size();i++) {
                Movie movie=MovieCreator.build(mMp4List.get(i));
                moviesList.add(movie);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Movie m : moviesList) {
            for (Track t : m.getTracks()) {
                if (t.getHandler().equals("soun")) {
                    audioTracks.add(t);
                }
                if (t.getHandler().equals("vide")) {
                    videoTracks.add(t);
                }
            }
        }

        Movie result = new Movie();

        try {
            if (audioTracks.size() > 0) {
                result.addTrack(new AppendTrack(audioTracks.toArray(new Track[audioTracks.size()])));
            }
            if (videoTracks.size() > 0) {
                result.addTrack(new AppendTrack(videoTracks.toArray(new Track[videoTracks.size()])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Container out = new DefaultMp4Builder().build(result);

        try {
            FileChannel fc = new FileOutputStream(new File(outputpath)).getChannel();
            out.writeContainer(fc);
            fc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        moviesList.clear();
    }


    /**
     * 将 AAC 和 MP4 进行混合[替换了视频的音轨]
     *
     * @param aacPath .aac
     * @param mp4Path .mp4
     * @param outPath .mp4
     */
    public static boolean muxAacMp4(String aacPath, String mp4Path, String outPath) {
        boolean flag=false;
        try {
            AACTrackImpl aacTrack = new AACTrackImpl(new FileDataSourceImpl(aacPath));
            Movie videoMovie = MovieCreator.build(mp4Path);
            Track videoTracks = null;// 获取视频的单纯视频部分
            for (Track videoMovieTrack : videoMovie.getTracks()) {
                if ("vide".equals(videoMovieTrack.getHandler())) {
                    videoTracks = videoMovieTrack;
                }
            }

            Movie resultMovie = new Movie();
            resultMovie.addTrack(videoTracks);// 视频部分
            resultMovie.addTrack(aacTrack);// 音频部分

            Container out = new DefaultMp4Builder().build(resultMovie);
            FileOutputStream fos = new FileOutputStream(new File(outPath));
            out.writeContainer(fos.getChannel());
            fos.close();
            flag=true;
            Log.e("update_tag","merge finish");
        } catch (Exception e) {
            e.printStackTrace();
            flag=false;
        }
        return flag;
    }



    /**
     * 将 M4A 和 MP4 进行混合[替换了视频的音轨]
     *
     * @param m4aPath .m4a[同样可以使用.mp4]
     * @param mp4Path .mp4
     * @param outPath .mp4
     */
    public static void muxM4AMp4(String m4aPath, String mp4Path, String outPath) throws IOException {
        Movie audioMovie = MovieCreator.build(m4aPath);
        Track audioTracks = null;// 获取视频的单纯音频部分
        for (Track audioMovieTrack : audioMovie.getTracks()) {
            if ("soun".equals(audioMovieTrack.getHandler())) {
                audioTracks = audioMovieTrack;
            }
        }

        Movie videoMovie = MovieCreator.build(mp4Path);
        Track videoTracks = null;// 获取视频的单纯视频部分
        for (Track videoMovieTrack : videoMovie.getTracks()) {
            if ("vide".equals(videoMovieTrack.getHandler())) {
                videoTracks = videoMovieTrack;
            }
        }

        Movie resultMovie = new Movie();
        resultMovie.addTrack(videoTracks);// 视频部分
        resultMovie.addTrack(audioTracks);// 音频部分

        Container out = new DefaultMp4Builder().build(resultMovie);
        FileOutputStream fos = new FileOutputStream(new File(outPath));
        out.writeContainer(fos.getChannel());
        fos.close();
    }


    /**
     * 分离mp4视频的音频部分，只保留视频部分
     *
     * @param mp4Path .mp4
     * @param outPath .mp4
     */
    public static void splitMp4(String mp4Path, String outPath){

        try{
            Movie videoMovie = MovieCreator.build(mp4Path);
            Track videoTracks = null;// 获取视频的单纯视频部分
            for (Track videoMovieTrack : videoMovie.getTracks()) {
                if ("vide".equals(videoMovieTrack.getHandler())) {
                    videoTracks = videoMovieTrack;
                }
            }

            Movie resultMovie = new Movie();
            resultMovie.addTrack(videoTracks);// 视频部分

            Container out = new DefaultMp4Builder().build(resultMovie);
            FileOutputStream fos = new FileOutputStream(new File(outPath));
            out.writeContainer(fos.getChannel());
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    /**
     * 分离mp4的视频部分，只保留音频部分
     *
     * @param mp4Path .mp4
     * @param outPath .aac
     */
    public static void splitAac(String mp4Path, String outPath){

        try{
            Movie videoMovie = MovieCreator.build(mp4Path);
            Track videoTracks = null;// 获取音频的单纯视频部分
            for (Track videoMovieTrack : videoMovie.getTracks()) {
                if ("soun".equals(videoMovieTrack.getHandler())) {
                    videoTracks = videoMovieTrack;
                }
            }

            Movie resultMovie = new Movie();
            resultMovie.addTrack(videoTracks);// 音频部分

            Container out = new DefaultMp4Builder().build(resultMovie);
            FileOutputStream fos = new FileOutputStream(new File(outPath));
            out.writeContainer(fos.getChannel());
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    /**
     * 分离mp4视频的音频部分，只保留视频部分
     *
     * @param mp4Path .mp4
     * @param mp4OutPath  mp4视频输出路径
     * @param aacOutPath  aac视频输出路径
     */
    public static void splitVideo(String mp4Path, String mp4OutPath,String aacOutPath){

        try{
            Movie videoMovie = MovieCreator.build(mp4Path);
            Track videTracks = null;// 获取视频的单纯视频部分
            Track sounTracks = null;// 获取视频的单纯音频部分

            for (Track videoMovieTrack : videoMovie.getTracks()) {
                if ("vide".equals(videoMovieTrack.getHandler())) {
                    videTracks = videoMovieTrack;
                }
                if ("soun".equals(videoMovieTrack.getHandler())) {
                    sounTracks = videoMovieTrack;
                }
            }

            Movie videMovie = new Movie();
            videMovie.addTrack(videTracks);// 视频部分

            Movie sounMovie = new Movie();
            sounMovie.addTrack(sounTracks);// 音频部分

            // 视频部分
            Container videout = new DefaultMp4Builder().build(videMovie);
            FileOutputStream videfos = new FileOutputStream(new File(mp4OutPath));
            videout.writeContainer(videfos.getChannel());
            videfos.close();

            // 音频部分
            Container sounout = new DefaultMp4Builder().build(sounMovie);
            FileOutputStream sounfos = new FileOutputStream(new File(aacOutPath));
            sounout.writeContainer(sounfos.getChannel());
            sounfos.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    /**
     * 对 Mp4 添加字幕
     *
     * @param mp4Path .mp4 添加字幕之前
     * @param outPath .mp4 添加字幕之后
     */
    public static void addSubtitles(String mp4Path, String outPath) throws IOException {
        Movie videoMovie = MovieCreator.build(mp4Path);

        TextTrackImpl subTitleEng = new TextTrackImpl();// 实例化文本通道对象
        subTitleEng.getTrackMetaData().setLanguage("eng");// 设置元数据(语言)

        subTitleEng.getSubs().add(new TextTrackImpl.Line(0, 1000, "Five"));// 参数时间毫秒值
        subTitleEng.getSubs().add(new TextTrackImpl.Line(1000, 2000, "Four"));
        subTitleEng.getSubs().add(new TextTrackImpl.Line(2000, 3000, "Three"));
        subTitleEng.getSubs().add(new TextTrackImpl.Line(3000, 4000, "Two"));
        subTitleEng.getSubs().add(new TextTrackImpl.Line(4000, 5000, "one"));
        subTitleEng.getSubs().add(new TextTrackImpl.Line(5001, 5002, " "));// 省略去测试
        videoMovie.addTrack(subTitleEng);// 将字幕通道添加进视频Movie对象中

        Container out = new DefaultMp4Builder().build(videoMovie);
        FileOutputStream fos = new FileOutputStream(new File(outPath));
        out.writeContainer(fos.getChannel());
        fos.close();
    }
    /**
     * 截取指定时间段的视频
     *
     * @param inPath 视频的路径
     * @param begin  需要截取的开始时间
     * @param end    截取的结束时间
     * @throws IOException
     */
   static String TAG ="截取指定时间段的视频";
    public static void clipVideo(String inPath, String outPath, double begin, double end)
            throws IOException {
        Movie movie = MovieCreator.build(inPath);

        List<Track> tracks = movie.getTracks();
        movie.setTracks(new LinkedList<Track>());
        // remove all tracks we will create new tracks from the old

        double startTime1 = begin;
        double endTime1 = end;
        // double startTime2 = 30;
        // double endTime2 = 40;

        boolean timeCorrected = false;

        // Here we try to find a track that has sync samples. Since we can only
        // start decoding
        // at such a sample we SHOULD make sure that the start of the new
        // fragment is exactly
        // such a frame
        for (Track track : tracks) {
            if (track.getSyncSamples() != null
                    && track.getSyncSamples().length > 0) {
                if (timeCorrected) {
                    // This exception here could be a false positive in case we
                    // have multiple tracks
                    // with sync samples at exactly the same positions. E.g. a
                    // single movie containing
                    // multiple qualities of the same video (Microsoft Smooth
                    // Streaming file)
                    Log.e(TAG,
                            "The startTime has already been corrected by another track with SyncSample. Not Supported.");
                    throw new RuntimeException(
                            "The startTime has already been corrected by another track with SyncSample. Not Supported.");
                }
                startTime1 = correctTimeToSyncSample(track, startTime1, false);
                endTime1 = correctTimeToSyncSample(track, endTime1, true);
                // startTime2 = correctTimeToSyncSample(track, startTime2,
                // false);
                // endTime2 = correctTimeToSyncSample(track, endTime2, true);
                timeCorrected = true;
            }
        }

        for (Track track : tracks) {
            long currentSample = 0;
            double currentTime = 0;
            double lastTime = 0;
            long startSample1 = -1;
            long endSample1 = -1;
            // long startSample2 = -1;
            // long endSample2 = -1;

            for (int i = 0; i < track.getSampleDurations().length; i++) {
                long delta = track.getSampleDurations()[i];

                if (currentTime > lastTime && currentTime <= startTime1) {
                    // current sample is still before the new starttime
                    startSample1 = currentSample;
                }
                if (currentTime > lastTime && currentTime <= endTime1) {
                    // current sample is after the new start time and still
                    // before the new endtime
                    endSample1 = currentSample;
                }
                // if (currentTime > lastTime && currentTime <= startTime2) {
                // // current sample is still before the new starttime
                // startSample2 = currentSample;
                // }
                // if (currentTime > lastTime && currentTime <= endTime2) {
                // // current sample is after the new start time and still
                // before the new endtime
                // endSample2 = currentSample;
                // }
                lastTime = currentTime;
                currentTime += (double) delta
                        / (double) track.getTrackMetaData().getTimescale();
                currentSample++;
            }
            Log.v("","===========");
            Log.v("",""+track.getSamples());
            movie.addTrack(new CroppedTrack(track, startSample1, endSample1));// new
            // AppendTrack(new
            // CroppedTrack(track,
            // startSample1,
            // endSample1),
            // new
            // CroppedTrack(track,
            // startSample2,
            // endSample2)));
        }
        long start1 = System.currentTimeMillis();
        Container out = new DefaultMp4Builder().build(movie);
        long start2 = System.currentTimeMillis();
        FileOutputStream fos = new FileOutputStream(outPath);
        FileChannel fc = fos.getChannel();
        out.writeContainer(fc);

        fc.close();
        fos.close();
        long start3 = System.currentTimeMillis();
        Log.e(TAG, "Building IsoFile took : " + (start2 - start1) + "ms");
        Log.e(TAG, "Writing IsoFile took : " + (start3 - start2) + "ms");
        Log.e(TAG,
                "Writing IsoFile speed : "
                        + (new File(String.format("output-%f-%f.mp4",
                        startTime1, endTime1)).length()
                        / (start3 - start2) / 1000) + "MB/s");
    }


    /**
     * 将 MP4 切割
     *
     * @param mp4Path    .mp4
     * @param fromSample 起始位置   不是传入的秒数
     * @param toSample   结束位置   不是传入的秒数
     * @param outPath    .mp4
     */
    public static void cropMp4(String mp4Path, long fromSample, long toSample, String outPath)throws Exception{



            Movie mp4Movie = MovieCreator.build(mp4Path);
            Track videoTracks = null;// 获取视频的单纯视频部分
            for (Track videoMovieTrack : mp4Movie.getTracks()) {
                if ("vide".equals(videoMovieTrack.getHandler())) {
                    videoTracks = videoMovieTrack;
                }
            }
            Track audioTracks = null;// 获取视频的单纯音频部分
            for (Track audioMovieTrack : mp4Movie.getTracks()) {
                if ("soun".equals(audioMovieTrack.getHandler())) {
                    audioTracks = audioMovieTrack;
                }
            }

            Movie resultMovie = new Movie();
            resultMovie.addTrack(new AppendTrack(new CroppedTrack(videoTracks, fromSample, toSample)));// 视频部分
            resultMovie.addTrack(new AppendTrack(new CroppedTrack(audioTracks, fromSample, toSample)));// 音频部分
            Container out = new DefaultMp4Builder().build(resultMovie);
            FileOutputStream fos = new FileOutputStream(new File(outPath));
            out.writeContainer(fos.getChannel());
            fos.close();

    }



    /**
     * 截取指定时间段的视频
     * @param path 视频的路径
     * @param begin 需要截取的开始时间
     * @param end 截取的结束时间
     * @throws IOException
     */
    public static void clipVideo(String path, double begin, double end,String outPaht)
            throws IOException {

        // Movie movie = new MovieCreator().build(new
        // RandomAccessFile("/home/sannies/suckerpunch-distantplanet_h1080p/suckerpunch-distantplanet_h1080p.mov",
        // "r").getChannel());
        Movie movie = MovieCreator.build(path);
        List<Track> tracks = movie.getTracks();
        movie.setTracks(new LinkedList<Track>());
        // remove all tracks we will create new tracks from the old
        double startTime1 = begin;
        double endTime1 = end;
        // double startTime2 = 30;
        // double endTime2 = 40;
        boolean timeCorrected = false;
        // Here we try to find a track that has sync samples. Since we can only
        // start decoding
        // at such a sample we SHOULD make sure that the start of the new
        // fragment is exactly
        // such a frame
        for (Track track : tracks) {
            if (track.getSyncSamples() != null
                    && track.getSyncSamples().length > 0) {
                if (timeCorrected) {
                    // This exception here could be a false positive in case we
                    // have multiple tracks
                    // with sync samples at exactly the same positions. E.g. a
                    // single movie containing
                    // multiple qualities of the same video (Microsoft Smooth
                    // Streaming file)
                    Log.e(TAG,
                            "The startTime has already been corrected by another track with SyncSample. Not Supported.");
                    throw new RuntimeException(
                            "The startTime has already been corrected by another track with SyncSample. Not Supported.");
                }
                startTime1 = correctTimeToSyncSample(track, startTime1, false);
                endTime1 = correctTimeToSyncSample(track, endTime1, true);
                // startTime2 = correctTimeToSyncSample(track, startTime2,
                // false);
                // endTime2 = correctTimeToSyncSample(track, endTime2, true);
                timeCorrected = true;
            }
        }
        for (Track track : tracks) {
            long currentSample = 0;
            double currentTime = 0;
            double lastTime = 0;
            long startSample1 = -1;
            long endSample1 = -1;
            // long startSample2 = -1;
            // long endSample2 = -1;
            for (int i = 0; i < track.getSampleDurations().length; i++) {
                long delta = track.getSampleDurations()[i];
                if (currentTime > lastTime && currentTime <= startTime1) {
                    // current sample is still before the new starttime
                    startSample1 = currentSample;
                }
                if (currentTime > lastTime && currentTime <= endTime1) {
                    // current sample is after the new start time and still
                    // before the new endtime
                    endSample1 = currentSample;
                }
                // if (currentTime > lastTime && currentTime <= startTime2) {
                // // current sample is still before the new starttime
                // startSample2 = currentSample;
                // }
                // if (currentTime > lastTime && currentTime <= endTime2) {
                // // current sample is after the new start time and still
                // before the new endtime
                // endSample2 = currentSample;
                // }
                lastTime = currentTime;
                currentTime += (double) delta
                        / (double) track.getTrackMetaData().getTimescale();
                currentSample++;
            }
            movie.addTrack(new CroppedTrack(track, startSample1, endSample1));// new
            // AppendTrack(new
            // CroppedTrack(track,
            // startSample1,
            // endSample1),
            // new
            // CroppedTrack(track,
            // startSample2,
            // endSample2)));
        }
        long start1 = System.currentTimeMillis();
        Container out = new DefaultMp4Builder().build(movie);
        long start2 = System.currentTimeMillis();
        FileOutputStream fos = new FileOutputStream(outPaht);
        FileChannel fc = fos.getChannel();
        out.writeContainer(fc);
        fc.close();
        fos.close();
        long start3 = System.currentTimeMillis();
        Log.e(TAG, "Building IsoFile took : " + (start2 - start1) + "ms");
        Log.e(TAG, "Writing IsoFile took : " + (start3 - start2) + "ms");
    /*    Log.e(TAG,
                "Writing IsoFile speed : "
                        + (new File(String.format("output-%f-%f.mp4",
                        startTime1, endTime1)).length()
                        / (start3 - start2) / 1000) + "MB/s");*/
    }
    private static double correctTimeToSyncSample(Track track, double cutHere,
                                                  boolean next) {
        double[] timeOfSyncSamples = new double[track.getSyncSamples().length];
        long currentSample = 0;
        double currentTime = 0;
        for (int i = 0; i < track.getSampleDurations().length; i++) {
            long delta = track.getSampleDurations()[i];
            if (Arrays.binarySearch(track.getSyncSamples(), currentSample + 1) >= 0) {
                // samples always start with 1 but we start with zero therefore
                // +1
                timeOfSyncSamples[Arrays.binarySearch(track.getSyncSamples(),
                        currentSample + 1)] = currentTime;
            }
            currentTime += (double) delta
                    / (double) track.getTrackMetaData().getTimescale();
            currentSample++;
        }
        double previous = 0;
        for (double timeOfSyncSample : timeOfSyncSamples) {
            if (timeOfSyncSample > cutHere) {
                if (next) {
                    return timeOfSyncSample;
                } else {
                    return previous;
                }
            }
            previous = timeOfSyncSample;
        }
        return timeOfSyncSamples[timeOfSyncSamples.length - 1];
    }
}