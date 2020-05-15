package com.example.cj.videoeditor.gpufilter.helper;

import com.example.cj.videoeditor.gpufilter.basefilter.GPUImageFilter;
import com.example.cj.videoeditor.gpufilter.basefilter.gpuimage.GPUImageBrightnessFilter;
import com.example.cj.videoeditor.gpufilter.basefilter.gpuimage.GPUImageContrastFilter;
import com.example.cj.videoeditor.gpufilter.basefilter.gpuimage.GPUImageExposureFilter;
import com.example.cj.videoeditor.gpufilter.basefilter.gpuimage.GPUImageHueFilter;
import com.example.cj.videoeditor.gpufilter.basefilter.gpuimage.GPUImageSaturationFilter;
import com.example.cj.videoeditor.gpufilter.basefilter.gpuimage.GPUImageSharpenFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicAmaroFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicAntiqueFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicBlackCatFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicBrannanFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicBrooklynFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicCalmFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicCoolFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicCrayonFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicEarlyBirdFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicEmeraldFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicEvergreenFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicFairytaleFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicFreudFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicHealthyFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicHefeFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicHudsonFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicImageAdjustFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicInkwellFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicKevinFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicLatteFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicLomoFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicN1977Filter;
import com.example.cj.videoeditor.gpufilter.filter.MagicNashvilleFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicNostalgiaFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicPixarFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicRiseFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicRomanceFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicSakuraFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicSierraFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicSketchFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicSkinWhitenFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicSunriseFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicSunsetFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicSutroFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicSweetsFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicTenderFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicToasterFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicValenciaFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicWaldenFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicWarmFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicWhiteCatFilter;
import com.example.cj.videoeditor.gpufilter.filter.MagicXproIIFilter;



public class MagicFilterFactory {
    private static MagicFilterType filterType = MagicFilterType.NONE;

    public static GPUImageFilter initFilters(MagicFilterType type){
        filterType = type;
        switch (type) {
            case WHITECAT:
                return new MagicWhiteCatFilter();
            case BLACKCAT:
                return new MagicBlackCatFilter();
            case SKINWHITEN:
                return new MagicSkinWhitenFilter();
            case ROMANCE:
                return new MagicRomanceFilter();
            case SAKURA:
                return new MagicSakuraFilter();
            case AMARO:
                return new MagicAmaroFilter();
            case WALDEN:
                return new MagicWaldenFilter();
            case ANTIQUE:
                return new MagicAntiqueFilter();
            case CALM:
                return new MagicCalmFilter();
            case BRANNAN:
                return new MagicBrannanFilter();
            case BROOKLYN:
                return new MagicBrooklynFilter();
            case EARLYBIRD:
                return new MagicEarlyBirdFilter();
            case FREUD:
                return new MagicFreudFilter();
            case HEFE:
                return new MagicHefeFilter();
            case HUDSON:
                return new MagicHudsonFilter();
            case INKWELL:
                return new MagicInkwellFilter();
            case KEVIN:
                return new MagicKevinFilter();
            case LOMO:
                return new MagicLomoFilter();
            case N1977:
                return new MagicN1977Filter();
            case NASHVILLE:
                return new MagicNashvilleFilter();
            case PIXAR:
                return new MagicPixarFilter();
            case RISE:
                return new MagicRiseFilter();
            case SIERRA:
                return new MagicSierraFilter();
            case SUTRO:
                return new MagicSutroFilter();
            case TOASTER2:
                return new MagicToasterFilter();
            case VALENCIA:
                return new MagicValenciaFilter();
            case XPROII:
                return new MagicXproIIFilter();
            case EVERGREEN:
                return new MagicEvergreenFilter();
            case HEALTHY:
                return new MagicHealthyFilter();
            case COOL:
                return new MagicCoolFilter();
            case EMERALD:
                return new MagicEmeraldFilter();
            case LATTE:
                return new MagicLatteFilter();
            case WARM:
                return new MagicWarmFilter();
            case TENDER:
                return new MagicTenderFilter();
            case SWEETS:
                return new MagicSweetsFilter();
            case NOSTALGIA:
                return new MagicNostalgiaFilter();
            case FAIRYTALE:
                return new MagicFairytaleFilter();
            case SUNRISE:
                return new MagicSunriseFilter();
            case SUNSET:
                return new MagicSunsetFilter();
            case CRAYON:
                return new MagicCrayonFilter();
            case SKETCH:
                return new MagicSketchFilter();
            //image adjust
            case BRIGHTNESS:
                return new GPUImageBrightnessFilter();
            case CONTRAST:
                return new GPUImageContrastFilter();
            case EXPOSURE:
                return new GPUImageExposureFilter();
            case HUE:
                return new GPUImageHueFilter();
            case SATURATION:
                return new GPUImageSaturationFilter();
            case SHARPEN:
                return new GPUImageSharpenFilter();
            case IMAGE_ADJUST:
                return new MagicImageAdjustFilter();
            default:
                return null;
        }
    }

    public MagicFilterType getCurrentFilterType(){
        return filterType;
    }
}
