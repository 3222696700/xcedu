package com.xuecheng.manage_cms_client.util;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Auther:ghost
 * @Date:2021/9/11
 * @Description:com.xuecheng.manage_cms.util
 * @version:1.0
 */
public class GridFSUtils {

    private GridFsTemplate gridFsTemplate;

    private GridFSBucket gridFSBucket;

    public GridFSUtils(GridFsTemplate gridFsTemplate, GridFSBucket gridFSBucket) {
        this.gridFsTemplate = gridFsTemplate;
        this.gridFSBucket = gridFSBucket;
    }

    public ObjectId uploadFileToMongoDB(InputStream inputStream,String fileName) {
        return gridFsTemplate.store(inputStream, fileName);
    }

    public InputStream downloadFileFromMongoDB(String fileId) throws IOException {
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));

        assert gridFSFile != null;

        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());

        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);

        return gridFsResource.getInputStream();
    }
}
