package com.kidoo.customer.mvp.view.usecase;

/**
 * User: ShaudXiao
 * Date: 2017-09-30
 * Time: 15:47
 * Company: zx
 * Description: Mvp domain layer 减轻了 Presenter 的体量， use cases 定义了每个业务的具体操作，细化了业务粒度
 * FIXME
 */


public abstract class UseCase<Q extends UseCase.RequestValues, P extends UseCase.ResponseValue>  {

    private Q mRequestVaule;

    public void setRequestVaule(Q requestVaule) {
        mRequestVaule = requestVaule;
    }

    public abstract P execute(Q requestVaule);

    /**
     * Data passed to a request.
     */
    public interface RequestValues {
    }

    /**
     * Data received from a request.
     */
    public interface ResponseValue {
    }
}
